package com.asep.lockerapplication.entity;

import com.asep.lockerapplication.entity.variableEnum.PaymentStatus;
import com.asep.lockerapplication.entity.variableEnum.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Booking booking;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
