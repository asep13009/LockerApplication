package com.asep.lockerapplication.dto.locker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LockerOpenResponse {
    private Long bookingId;
    private String lockerNumber;

    private int remainingPasswordUses;
    private int failedLimit;
}

