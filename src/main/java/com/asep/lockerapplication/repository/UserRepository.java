package com.asep.lockerapplication.repository;

import com.asep.lockerapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);
    boolean existsByKtp(String ktp);
    boolean existsByEmail(String email);
}
