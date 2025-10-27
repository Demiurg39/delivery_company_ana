package com.anateam.service;

import com.anateam.dto.PaymentRequestDto;
import com.anateam.dto.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto requestDto, Integer customerId);

    PaymentResponseDto getPaymentByOrderId(Integer orderId);
}
