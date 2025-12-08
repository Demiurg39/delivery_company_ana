package com.anateam.dto;

public record AuthResponseDto(
    String accessToken,
    String refreshToken
) {}
