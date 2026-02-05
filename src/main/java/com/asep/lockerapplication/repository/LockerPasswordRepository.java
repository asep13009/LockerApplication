package com.asep.lockerapplication.repository;

import com.asep.lockerapplication.entity.LockerPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LockerPasswordRepository extends JpaRepository<LockerPassword, Long> {

    Optional<LockerPassword> findByBooking_IdAndLocker_Id(Long bookingId, Long lockerId);

}

