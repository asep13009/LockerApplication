package com.asep.lockerapplication.dto.locker;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LockerPasswordResponse {
    private String lockerNumber;
    private String password;
}
