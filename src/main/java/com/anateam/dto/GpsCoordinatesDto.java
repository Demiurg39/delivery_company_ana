package com.anateam.dto;

import java.math.BigDecimal;

public record GpsCoordinatesDto(
    BigDecimal latitude,
    BigDecimal longitude
) {}

