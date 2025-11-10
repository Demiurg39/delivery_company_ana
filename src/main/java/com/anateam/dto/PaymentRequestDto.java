package com.anateam.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(
    Integer orderId,
    BigDecimal amount,
    String paymentMethod
) {}
