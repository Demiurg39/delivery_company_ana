package com.anateam.dto;

public record CourierProfileDto(
    Integer userId,
    String fullName,
    String phoneNumber,
    String status,
    String vehicleType
) {}
