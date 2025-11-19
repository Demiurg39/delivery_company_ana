package com.anateam.service;

import com.anateam.dto.PaymentRequestDto;
import com.anateam.dto.PaymentResponseDto;
import com.anateam.dto.PaymentUpdateDto;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto requestDto, Integer customerId);

    PaymentResponseDto getPaymentByOrderId(Integer orderId);

    PaymentResponseDto updatePayment(PaymentUpdateDto updateDto);
}
