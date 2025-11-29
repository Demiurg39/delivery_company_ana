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
import com.anateam.dto.UserRegistrationDto;
import com.anateam.dto.UserResponseDto;
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

    @PostMapping("/register/client")
    @Operation(summary = "Register a new client", description = "Creates a new client account and returns a JWT token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully registered"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "409", description = "Conflict - User with this phone number already exists")
    })
    public ResponseEntity<UserResponseDto> registerClient(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.registerClient(registrationDto));
    }

    @PostMapping("/register/courier")
    @Operation(summary = "Register a new courier", description = "Creates a new courier account and returns a JWT token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully registered"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "409", description = "Conflict - User with this phone number already exists")
    })
    public ResponseEntity<UserResponseDto> registerCourier(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.registerCourier(registrationDto));
    }

    @PostMapping("/register/admin")
    @Operation(summary = "Register a new admin", description = "Creates a new admin account and returns a JWT token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully registered"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "409", description = "Conflict - User with this phone number already exists")
    })
    public ResponseEntity<UserResponseDto> registerAdmin(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.registerAdmin(registrationDto));
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
}
