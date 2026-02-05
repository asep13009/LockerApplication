package com.asep.lockerapplication.controller;

import com.asep.lockerapplication.dto.locker.CreateLockerRequest;
import com.asep.lockerapplication.entity.Locker;
import com.asep.lockerapplication.entity.variableEnum.LockerStatus;
import com.asep.lockerapplication.handler.exeption.BusinessException;
import com.asep.lockerapplication.handler.globalResponse.ApiResponse;
import com.asep.lockerapplication.repository.LockerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/lockers")
@RequiredArgsConstructor
public class AdminLockerController {

    private final LockerRepository lockerRepo;

    @PostMapping("/create")
    public ApiResponse<Locker> createLocker(
            @Valid @RequestBody CreateLockerRequest request
    ) {
        if (lockerRepo.existsByLockerNumber(request.getLockerNumber())) {
            throw new BusinessException("Locker number already exists");
        }

        Locker locker = new Locker();
        locker.setLockerNumber(request.getLockerNumber());
        locker.setStatus(LockerStatus.AVAILABLE);

        lockerRepo.save(locker);

        return ApiResponse.success(locker);
    }

    @GetMapping("/all")
    public ApiResponse<List<Locker>> getAllLockers() {
        return ApiResponse.success(lockerRepo.findAll());
    }
}
