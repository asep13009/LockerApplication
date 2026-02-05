package com.asep.lockerapplication.service;

import com.asep.lockerapplication.dto.user.RegisterUserRequest;
import com.asep.lockerapplication.dto.user.UserResponse;

public interface UserService {
    UserResponse register(RegisterUserRequest request);
}
