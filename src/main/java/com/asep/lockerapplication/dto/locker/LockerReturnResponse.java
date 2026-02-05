package com.asep.lockerapplication.dto.locker;

import com.asep.lockerapplication.entity.Locker;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class LockerReturnResponse {
    private Long bookingId;
    private BigDecimal deposit;
    private Long lateDays;
    private BigDecimal fine;
    private BigDecimal penalty;
    private int lockers;
    private BigDecimal refund;
}

