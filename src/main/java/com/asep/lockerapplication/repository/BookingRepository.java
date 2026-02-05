package com.asep.lockerapplication.repository;

import com.asep.lockerapplication.entity.Booking;
import com.asep.lockerapplication.entity.variableEnum.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
    select count(l)
    from Booking b
    join b.lockers l
    where b.user.id = :userId
      and b.status = 'ACTIVE'
    """)
    long countActiveLockersByUser(@Param("userId") Long userId);

    Optional<Booking> findByIdAndStatus(Long id, BookingStatus status);



}
