package com.anateam.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDto(
    @Schema(description = "ID of the order to pay for", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    Integer orderId,

    @Schema(description = "Amount to pay", example = "250.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    BigDecimal amount,

    @Schema(description = "Payment method (CARD, CASH)", example = "CARD")
    String paymentMethod
) {}
