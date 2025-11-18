package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CourierStatusUpdateDto(
    @NotNull
    @Schema(description = "New status for the courier (e.g., INACTIVE, ACTIVE, ON_ORDER)", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    String status
) {}

