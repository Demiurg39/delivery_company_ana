package com.anateam.dto;

import com.anateam.entity.GpsCoordinates;

public record OrderCreationDto(
    GpsCoordinates pickupCoordinates,
    GpsCoordinates deliveryCoordinates,
    String pickupAddress,
    String deliveryAddress,
    String description
) {}
