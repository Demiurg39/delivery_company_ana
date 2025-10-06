package com.anateam.dto;

import java.math.BigDecimal;

public record OrderResponseDto(
    Integer id,
    Integer customerId,
    Integer courierId,
    String status,
    String deliveryAddress,
    String pickupAddress,
    BigDecimal pickupCoordinates,
    BigDecimal deliveryCoordinates,
    BigDecimal price,
    Integer estimatedMinutes,
    String createdAt
) {}

