package com.anateam.dto;

import java.math.BigDecimal;

public record OrderResponseDto(
    Integer id,
    Integer customerId,
    Integer courierId,
    String status,
    String pickupAddress,
    String deliveryAddress,
    GpsCoordinatesDto pickupCoordinates,
    GpsCoordinatesDto deliveryCoordinates,
    BigDecimal price,
    Integer estimatedMinutes,
    String createdAt
) {}
