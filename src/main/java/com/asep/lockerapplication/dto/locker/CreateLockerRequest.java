package com.asep.lockerapplication.dto.locker;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateLockerRequest {
    @NotBlank(message = "Locker number is required")
    private String lockerNumber;
}
