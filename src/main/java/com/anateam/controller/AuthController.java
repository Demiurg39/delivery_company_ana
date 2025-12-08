package com.anateam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anateam.dto.AuthResponseDto;
import com.anateam.dto.LoginDto;
import com.anateam.dto.SendCodeDto;
import com.anateam.dto.UserRegistrationDto;
import com.anateam.dto.UserResponseDto;
import com.anateam.dto.VerifyCodeDto;
import com.anateam.entity.UserRole;
import com.anateam.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Registration and Authentication for users")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/customer")
    @Operation(summary = "Register a new customer", description = "Creates a new customer account and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Conflict - User with this phone number already exists")
    })
    public ResponseEntity<UserResponseDto> registerCustomer(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.register(registrationDto, UserRole.CUSTOMER));
    }

    @PostMapping("/register/courier")
    @Operation(summary = "Register a new courier", description = "Creates a new courier account and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Conflict - User with this phone number already exists")
    })
    public ResponseEntity<UserResponseDto> registerCourier(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.register(registrationDto, UserRole.COURIER));
    }

    @PostMapping("/register/admin")
    @Operation(summary = "Register a new admin", description = "Creates a new admin account and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Conflict - User with this phone number already exists")
    })
    public ResponseEntity<UserResponseDto> registerAdmin(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.register(registrationDto, UserRole.ADMIN));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user by phone number and password, returning a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login"),
            @ApiResponse(responseCode = "400", description = "Invalid login request format"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/send-code")
    @Operation(summary = "Send verification code", description = "Sends a verification code via SMS to the provided phone number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification code sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid phone number"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> sendCode(@Valid @RequestBody SendCodeDto sendCodeDto) {
        authService.sendVerificationCode(sendCodeDto.phoneNumber());
        return ResponseEntity.ok("Verification code sent successfully");
    }

    @PostMapping("/verify-code")
    @Operation(summary = "Verify code and get token", description = "Verifies the SMS code and returns a JWT token upon successful verification.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code verified successfully, token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired code"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<AuthResponseDto> verifyCode(@Valid @RequestBody VerifyCodeDto verifyCodeDto) {
        return ResponseEntity.ok(authService.verifyCode(verifyCodeDto.phoneNumber(), verifyCodeDto.code()));
    }
}
