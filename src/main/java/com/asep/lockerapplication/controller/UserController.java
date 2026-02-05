package com.asep.lockerapplication.controller;

import com.asep.lockerapplication.dto.user.RegisterUserRequest;
import com.asep.lockerapplication.dto.user.UserResponse;
import com.asep.lockerapplication.handler.globalResponse.ApiResponse;
import com.asep.lockerapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody  RegisterUserRequest request) {
        return ApiResponse.success(userService.register(request));
    }
}
