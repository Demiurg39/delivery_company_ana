package com.anateam.service;

import com.anateam.dto.AuthResponseDto;
import com.anateam.dto.LoginDto;
import com.anateam.dto.UserRegistrationDto;
import com.anateam.dto.UserResponseDto;
import com.anateam.entity.UserRole;

import jakarta.validation.constraints.NotBlank;

public interface AuthService {
    UserResponseDto register(UserRegistrationDto registrationDto, UserRole role);

    AuthResponseDto login(LoginDto loginDto);

    void sendVerificationCode(String phoneNumber);

    AuthResponseDto verifyCode(String phoneNumber, String code);

    public AuthResponseDto refreshToken(String requestRefreshToken);
}
