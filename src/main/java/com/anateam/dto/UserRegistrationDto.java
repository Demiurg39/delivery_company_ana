package com.anateam.dto;

public record UserRegistrationDto (
    String fullName,
    String phoneNumber,
    String password
) {}
