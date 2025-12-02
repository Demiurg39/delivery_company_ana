package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDto(
    @Schema(description = "User ID", example = "1")
    Integer id,

    @Schema(description = "Full Name", example = "John Doe")
    String fullName,

    @Schema(description = "Phone Number", example = "+1234567890")
    String phoneNumber,

    @Schema(description = "User Role", example = "CUSTOMER")
    String role,

    @Schema(description = "Account Creation Date", example = "2023-10-01T12:00:00")
    String createdAt
) {}
