package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserUpdateDto(
    @Schema(description = "New full name", example = "Jane Doe")
    String fullName,

    @Schema(description = "New phone number", example = "88005553535")
    String phoneNumber
) {}

