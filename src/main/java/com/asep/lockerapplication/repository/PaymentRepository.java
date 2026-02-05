package com.asep.lockerapplication.repository;

import com.asep.lockerapplication.entity.Payment;
import com.asep.lockerapplication.entity.variableEnum.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByTypeAndBooking_Id(PaymentType type, Long bookingId);

}
