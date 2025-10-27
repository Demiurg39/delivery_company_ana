package com.anateam.controller;

import com.anateam.dto.UserRegistrationDto;
import com.anateam.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@NoArgsConstructor
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Validated
    public ResponseEntity<Void>
    registerUser(@RequestBody UserRegistrationDto registrationDto) {
        userService.register(registrationDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
