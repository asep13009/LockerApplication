package com.asep.lockerapplication.impl;

import com.asep.lockerapplication.dto.user.RegisterUserRequest;
import com.asep.lockerapplication.dto.user.UserResponse;
import com.asep.lockerapplication.entity.User;
import com.asep.lockerapplication.handler.exeption.BusinessException;
import com.asep.lockerapplication.repository.UserRepository;
import com.asep.lockerapplication.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse register(RegisterUserRequest req) {

        if (userRepository.existsByPhone(req.getPhone())) {
            throw new BusinessException("Phone already registered");
        }

        if (userRepository.existsByKtp(req.getKtp())) {
            throw new BusinessException("KTP already registered");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        User user = new User();
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setKtp(req.getKtp());
        user.setEmail(req.getEmail());

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail()
        );
    }
}
