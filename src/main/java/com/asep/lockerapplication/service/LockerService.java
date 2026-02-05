package com.asep.lockerapplication.service;

import com.asep.lockerapplication.dto.booking.BookLockerRequest;
import com.asep.lockerapplication.dto.booking.BookingResponse;
import com.asep.lockerapplication.dto.locker.LockerOpenRequest;
import com.asep.lockerapplication.dto.locker.LockerReturnResponse;
import org.springframework.http.ResponseEntity;


public interface LockerService {
    BookingResponse book(BookLockerRequest request);
    ResponseEntity<Object> open(LockerOpenRequest request);
    LockerReturnResponse returnLocker(Long bookingId);
}

