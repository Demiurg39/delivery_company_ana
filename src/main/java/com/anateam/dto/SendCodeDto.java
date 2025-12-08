package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SendCodeDto(
        @Schema(description = "Phone number to send verification code", example = "996555123456", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank String phoneNumber) {
}
