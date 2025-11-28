package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
    @Schema(description = "New full name", example = "Jane Doe")
    @Size(min = 2, max = 100)
    String fullName,

    @Schema(description = "New phone number", example = "88005553535")
    @Size(min = 10, max = 15)
    String phoneNumber
) {}

