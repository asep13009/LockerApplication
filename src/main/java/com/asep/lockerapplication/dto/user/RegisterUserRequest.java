package com.asep.lockerapplication.dto.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @NotNull
    private String name;

    @NotBlank(message = "Phone is required")
    @NotNull
    private String phone;
    @NotBlank(message = "KTP is required")
    @NotNull
    private String ktp;
    @NotBlank(message = "Email is required")
    @NotNull
    @Email(message = "Invalid email format")
    private String email;
}

