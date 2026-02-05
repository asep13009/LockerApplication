package com.asep.lockerapplication.dto.booking;

import com.asep.lockerapplication.entity.Locker;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingResponse {
    private Long bookingId;
    private BigDecimal deposit;
    private List<Locker> lockers;

    public BookingResponse(Long bookingId, BigDecimal deposit, List<Locker> lockers) {
        this.bookingId=bookingId;
        this.deposit = deposit;
        this.lockers = lockers;
    }
}

