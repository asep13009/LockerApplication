package com.asep.lockerapplication.dto.booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookLockerRequest {
    @NotNull
    private Long userId;
    @NotNull
    private List<Long> lockerIds;
    @FutureOrPresent(message = "Start date must be in the present")
    private LocalDate startDate;
    @Future(message = "End Date must be in the future")
    private LocalDate endDate;
}
