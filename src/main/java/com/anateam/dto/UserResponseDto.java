package com.anateam.dto;

// TODO: add annotations for fields
public record UserResponseDto(
    Integer id,
    String fullName,
    String phoneNumber,
    String role,
    String createdAt
) {}
