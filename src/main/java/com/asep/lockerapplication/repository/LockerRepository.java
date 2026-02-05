package com.asep.lockerapplication.repository;

import com.asep.lockerapplication.entity.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
    boolean existsByLockerNumber(String lockerNumber);

    Optional<Locker> findByLockerNumber(String lockerNumber);
}

