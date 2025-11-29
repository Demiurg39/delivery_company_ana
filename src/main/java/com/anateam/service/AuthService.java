package com.anateam.service;

import com.anateam.dto.AuthResponseDto;
import com.anateam.dto.LoginDto;
import com.anateam.dto.UserRegistrationDto;
import com.anateam.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto registerCustomer(UserRegistrationDto registrationDto);
    UserResponseDto registerCourier(UserRegistrationDto registrationDto);
    UserResponseDto registerAdmin(UserRegistrationDto registrationDto);

    AuthResponseDto login(LoginDto loginDto);
}
