package com.asep.lockerapplication.entity;

import com.asep.lockerapplication.entity.variableEnum.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "booking_lockers",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "locker_id")
    )
    private Set<Locker> lockers = new HashSet<>();

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal depositAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.ACTIVE;
}
