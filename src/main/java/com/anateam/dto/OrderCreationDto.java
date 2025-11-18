package com.anateam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderCreationDto(

    @Schema(description = "Pickup address in text format", example = "123 Main St, Bishkek")
    String pickupAddress,

    @Schema(description = "Delivery address in text format", example = "456 Elm St, Bishkek")
    String deliveryAddress,

    @Schema(description = "GPS coordinates for pickup location", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    GpsCoordinatesDto pickupCoordinates,

    @Schema(description = "GPS coordinates for delivery location", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    GpsCoordinatesDto deliveryCoordinates,

    @Schema(description = "Additional details for the order", example = "Fragile package, call upon arrival")
    String description
) {}
