package com.anateam.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record GpsCoordinatesDto(
    @Schema(description = "Latitude", example = "42.8746", requiredMode = Schema.RequiredMode.REQUIRED)
    Double latitude,

    @Schema(description = "Longitude", example = "74.5698", requiredMode = Schema.RequiredMode.REQUIRED)
    Double longitude
) {}

