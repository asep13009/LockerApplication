package com.asep.lockerapplication.controller;

import com.asep.lockerapplication.dto.booking.BookLockerRequest;
import com.asep.lockerapplication.dto.booking.BookingResponse;
import com.asep.lockerapplication.dto.locker.LockerOpenRequest;
import com.asep.lockerapplication.dto.locker.LockerReturnResponse;
import com.asep.lockerapplication.handler.globalResponse.ApiResponse;
import com.asep.lockerapplication.service.LockerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lockers")
@RequiredArgsConstructor
public class LockerController {

    private final LockerService service;

    @PostMapping("/book")
    public ApiResponse<BookingResponse> book(@Valid @RequestBody BookLockerRequest req) {
        return ApiResponse.success(service.book(req));
    }

    @PostMapping("/open")
    public ResponseEntity<Object> open(@Valid @RequestBody LockerOpenRequest req) {
        return service.open(req);
    }

    @PostMapping("/return/{bookingId}")
    public ApiResponse<LockerReturnResponse> returnLocker(@PathVariable Long bookingId) {
        return ApiResponse.success(service.returnLocker(bookingId));
    }
}
