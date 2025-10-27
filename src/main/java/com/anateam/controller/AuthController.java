package com.anateam.controller;

import com.anateam.dto.LoginDto;
import com.anateam.dto.UserRegistrationDto;
import com.anateam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Validated
    public ResponseEntity<Void>
    registerUser(@RequestBody UserRegistrationDto registrationDto) {
        authService.register(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Validated
    public ResponseEntity<Void> registerUser(@RequestBody LoginDto loginDto) {
        authService.login(loginDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
