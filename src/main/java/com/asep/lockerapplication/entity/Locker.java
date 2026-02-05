package com.asep.lockerapplication.entity;

import com.asep.lockerapplication.entity.variableEnum.LockerStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String lockerNumber;

    @Enumerated(EnumType.STRING)
    private LockerStatus status = LockerStatus.AVAILABLE;
}
