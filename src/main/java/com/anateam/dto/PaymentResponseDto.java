package com.anateam.dto;

import java.math.BigDecimal;

public record PaymentResponseDto(
    Integer paymentId,
    Integer orderId,
    BigDecimal amount,
    String status, // "PENDING", "COMPLETED"
    String paymentMethod
) {}
