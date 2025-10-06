package com.anateam.dto;

public record OrderCreationDto(
    Integer customerId,
    Integer courierId,
    String deliveryAddress,
    String pickupAddress
) {}

