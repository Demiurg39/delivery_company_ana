package com.anateam.dto;

public record PaymentRequestDto(
    Integer orderId,
    String paymentMethod
) {}
