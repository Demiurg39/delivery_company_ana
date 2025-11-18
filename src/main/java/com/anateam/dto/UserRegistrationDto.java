package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserRegistrationDto(
    
    @Schema(description = "User phone number (login)", example = "89991234567", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank 
    String phoneNumber,

    @Schema(description = "Full name of the user", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String fullName,

    @Schema(description = "User password", example = "strongPass123!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String password,

    @Schema(description = "Role of the user: CUSTOMER or COURIER", example = "CUSTOMER", defaultValue = "CUSTOMER")
    String role
) {}
