package com.anateam.dto;

public record PaymentUpdateDto(
    Integer id,
    String status,
    String paymentMethod
) {}
