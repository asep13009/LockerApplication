package com.asep.lockerapplication.dto.locker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LockerOpenRequest {
    @NotNull
    private Long bookingId;
    @NotNull
    @NotBlank(message = "not empty")
    private String lockerNumber;
    @NotNull
    @NotBlank(message = "not empty")
    private String password;
}

