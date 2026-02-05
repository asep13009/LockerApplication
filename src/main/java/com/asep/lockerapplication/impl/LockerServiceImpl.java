package com.asep.lockerapplication.impl;

import com.asep.lockerapplication.dto.booking.BookLockerRequest;
import com.asep.lockerapplication.dto.booking.BookingResponse;
import com.asep.lockerapplication.dto.locker.LockerOpenRequest;
import com.asep.lockerapplication.dto.locker.LockerOpenResponse;
import com.asep.lockerapplication.dto.locker.LockerReturnResponse;
import com.asep.lockerapplication.entity.*;
import com.asep.lockerapplication.entity.variableEnum.*;
import com.asep.lockerapplication.handler.exeption.BusinessException;
import com.asep.lockerapplication.handler.globalResponse.ApiResponse;
import com.asep.lockerapplication.repository.*;
import com.asep.lockerapplication.service.EmailService;
import com.asep.lockerapplication.service.LockerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockerServiceImpl implements LockerService {

    private final UserRepository userRepo;
    private final LockerRepository lockerRepo;
    private final BookingRepository bookingRepo;
    private final LockerPasswordRepository passwordRepo;
    private final PaymentRepository paymentRepo;
    private final EmailService emailService;


    private static final BigDecimal DEPOSIT = BigDecimal.valueOf(10000);
    private static final BigDecimal FINE = BigDecimal.valueOf(5000);
    private static final BigDecimal PENALTY = BigDecimal.valueOf(25000);

    final int MAX_FAILED_ATTEMPTS = 3;
    final int MAX_USES = 2;

    @Transactional
    @Override
    public BookingResponse book(BookLockerRequest req) {

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new BusinessException("User not found"));

        if (bookingRepo.countActiveLockersByUser(user.getId()) + req.getLockerIds().size() > 3) {
            throw new BusinessException("Max 3 lockers");
        }

        List<Locker> lockers = lockerRepo.findAllById(req.getLockerIds());
        if(lockers.isEmpty()){
            throw new BusinessException("Locker not found");
        }

        lockers.forEach(l -> {
            if (l.getStatus() != LockerStatus.AVAILABLE) {
                throw new BusinessException("LOKER "+ l.getLockerNumber() + ", Locker not available");
            }
        });

        long days = ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate());
        System.out.println("days "+days);
        BigDecimal deposit = DEPOSIT
                .multiply(BigDecimal.valueOf(days))
                .multiply(BigDecimal.valueOf(lockers.size()));
        System.out.println("deposit "+deposit);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setLockers(new HashSet<>(lockers));
        booking.setStartDate(req.getStartDate());
        booking.setEndDate(req.getEndDate());
        booking.setDepositAmount(deposit);

        bookingRepo.save(booking);

        List<LockerPassword> lps= new ArrayList<>();

        lockers.forEach(l -> {
            l.setStatus(LockerStatus.BOOKED);
            lockerRepo.save(l);

            String raw = UUID.randomUUID().toString().substring(0, 6);
            LockerPassword lp = new LockerPassword();
            lp.setBooking(booking);
            lp.setLocker(l);
            lp.setPassword(raw);
            lps.add(lp);
            passwordRepo.save(lp);

        });

        emailService.sendLockerCredential(
                user.getEmail(), lps
        );

        paymentRepo.save(createPayment(booking, deposit, PaymentType.DEPOSIT, PaymentStatus.PAID));
        return new BookingResponse(booking.getId(), deposit, lockers);
    }


    @Transactional
    @Override
    public ResponseEntity<Object> open(LockerOpenRequest req) {
        System.out.println("req "+req);
        Locker locker = lockerRepo.findByLockerNumber(req.getLockerNumber())
                .orElseThrow(() -> new BusinessException("Locker number not found"));

        LockerPassword lp = passwordRepo
                .findByBooking_IdAndLocker_Id(
                        req.getBookingId(),
                        locker.getId()
                ).orElseThrow(() -> new BusinessException("Data number not found"));

        if ((lp.getStatus() != LockerPasswordStatus.ACTIVE) ) {
            return buildErrorResponse("Sorry, locker " + lp.getStatus() + "!!");
        }

        if (!req.getPassword().equals(lp.getPassword())) {
            return handleFailedAttempt(lp);
        }

        lp.markUsed(MAX_USES);
        passwordRepo.save(lp);

        return ResponseEntity
                .status(200)
                .body(ApiResponse.success(
                        LockerOpenResponse.builder()
                                .bookingId(req.getBookingId())
                                .lockerNumber(req.getLockerNumber())
                                .remainingPasswordUses(MAX_USES - lp.getUsedCount())
                                .failedLimit(MAX_FAILED_ATTEMPTS  - lp.getFailedCount())
                                .build()
                ));

    }

    @Override
    public LockerReturnResponse returnLocker(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BusinessException("Booking not found"));

        long lateDays = ChronoUnit.DAYS
                .between(booking.getEndDate(), LocalDate.now())+1;

        System.out.println("late day ::  "+lateDays);

        BigDecimal fine = BigDecimal.ZERO;
        if (lateDays > 0) {
            fine = FINE.multiply(BigDecimal.valueOf(lateDays));
            paymentRepo.save(createPayment(booking, fine, PaymentType.FINE, PaymentStatus.UNPAID));
        }



        List<Payment> payments = paymentRepo.findByTypeAndBooking_Id(PaymentType.PENALTY,booking.getId());
        BigDecimal countPenalty = BigDecimal.ZERO;
        BigDecimal countDeposit = BigDecimal.ZERO;
        for(Payment pay:payments){
            if(pay.getType().equals(PaymentType.PENALTY)){
                countPenalty = pay.getAmount().add(countPenalty);
            } else if (pay.getType().equals(PaymentType.DEPOSIT)){
                countDeposit = pay.getAmount().add(countDeposit);
            }
        }

        //jika ada deposit di hilangkan karena ada penalty
        if(countPenalty.compareTo(BigDecimal.ZERO)==0){
            countDeposit = BigDecimal.ZERO;
        }
        // ASUMSI JIKA MINUS USER HARUS BAYAR
        BigDecimal refund = countDeposit.subtract(fine).subtract(countPenalty);


        booking.setStatus(BookingStatus.COMPLETED);

        booking.getLockers().forEach(l -> {
            l.setStatus(LockerStatus.AVAILABLE);
            lockerRepo.save(l);
        });

        return LockerReturnResponse.builder()
                .bookingId(bookingId)
                .deposit(booking.getDepositAmount())
                .lateDays(lateDays)
                .fine(fine)
                .penalty(countPenalty)
                .refund(refund)
                .lockers(booking.getLockers().size())
                .build();
    }

    private Payment createPayment(
            Booking booking,
            BigDecimal amount,
            PaymentType type,
            PaymentStatus status
    ) {
        Payment p = new Payment();
        p.setBooking(booking);
        p.setAmount(amount);
        p.setType(type);
        p.setStatus(status);
        return p;
    }

    private ResponseEntity<Object> handleFailedAttempt(LockerPassword lp) {
        lp.markFailed(MAX_FAILED_ATTEMPTS);
        passwordRepo.save(lp);

        if (lp.getStatus() == LockerPasswordStatus.FORFEITED) {
            paymentRepo.save(createPayment(lp.getBooking(), PENALTY, PaymentType.PENALTY, PaymentStatus.UNPAID));
            return buildErrorResponse("Sorry, locker locked!!");
        }

        int tryAgain = MAX_FAILED_ATTEMPTS - lp.getFailedCount();
        return buildErrorResponse("Wrong password, try " + tryAgain + " again");
    }

    private ResponseEntity<Object> buildErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), message));
    }
}
