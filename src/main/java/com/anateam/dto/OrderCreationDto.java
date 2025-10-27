package com.anateam.dto;

public record OrderCreationDto(
    GpsCoordinatesDto pickupCoordinates,
    GpsCoordinatesDto deliveryCoordinates,
    String pickupAddress,
    String deliveryAddress,
    String description
) {}
