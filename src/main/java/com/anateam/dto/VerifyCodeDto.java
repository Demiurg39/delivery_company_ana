package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record VerifyCodeDto(
        @Schema(description = "Phone number", example = "996555123456", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String phoneNumber,

        @Schema(description = "Verification code received via SMS", example = "1234", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String code) {
}
