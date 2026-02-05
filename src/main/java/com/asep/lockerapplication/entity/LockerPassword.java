package com.asep.lockerapplication.entity;

import com.asep.lockerapplication.entity.variableEnum.LockerPasswordStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LockerPassword {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Booking booking;
    @ManyToOne
    private Locker locker;
    private String password;
    @Enumerated(EnumType.STRING)
    private LockerPasswordStatus status = LockerPasswordStatus.ACTIVE;

    private int usedCount = 0;
    private int failedCount =0;

    public void markUsed(int maxUses) {
        usedCount++;
        if (usedCount >= maxUses) {
            status = LockerPasswordStatus.EXPIRED;
        }
    }
    public void markFailed(int maxFailed) {
        failedCount++;
        if (failedCount >= maxFailed) {
            status = LockerPasswordStatus.FORFEITED;
        }
    }
}
