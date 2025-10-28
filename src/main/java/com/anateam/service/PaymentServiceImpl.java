package com.anateam.service;

import com.anateam.dto.PaymentRequestDto;
import com.anateam.dto.PaymentResponseDto;
import com.anateam.entity.Payment;
import com.anateam.repository.PaymentRepository;
import com.anateam.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto, Integer customerId) {
        Payment payment = new Payment();
        payment.setCustomerId(customerId);
        payment.setAmount(requestDto.getAmount());
        payment.setMethod(requestDto.getMethod());
        payment.setOrderId(requestDto.getOrderId());
        payment.setStatus("pending");

        paymentRepository.save(payment);

        return new PaymentResponseDto(payment);
    }
    @Override
    public PaymentResponseDto getPaymentByOrderId(Integer orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return new PaymentResponseDto(payment);
    }

}