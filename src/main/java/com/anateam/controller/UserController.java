package com.anateam.controller;

import com.anateam.dto.UserRegistrationDto;
import com.anateam.entity.User;
import com.anateam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User>
    registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User createdUser = userService.register(registrationDto);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
