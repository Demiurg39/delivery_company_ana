package com.anateam.service;

import org.springframework.stereotype.Service;

import com.anateam.dto.PaymentRequestDto;
import com.anateam.dto.PaymentResponseDto;
import com.anateam.entity.Order;
import com.anateam.entity.Payment;
import com.anateam.entity.PaymentMethod;
import com.anateam.entity.PaymentStatus;
import com.anateam.repository.OrderRepository;
import com.anateam.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    private PaymentResponseDto toPaymentResponseDto(Payment payment) {
        return new PaymentResponseDto(
            payment.getId(), payment.getOrder().getId(), payment.getAmount(),
            payment.getStatus().toString(),
            payment.getPaymentMethod().toString());
    }

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto,
                                            Integer customerId) {
        Order order =
            orderRepository.findById(requestDto.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(requestDto.amount());
        payment.setPaymentMethod(
            PaymentMethod.valueOf(requestDto.paymentMethod().toUpperCase()));
        payment.setStatus(PaymentStatus.PENDING);

        paymentRepository.save(payment);

        return toPaymentResponseDto(payment);
    }
    @Override
    public PaymentResponseDto getPaymentByOrderId(Integer orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(
            () -> new RuntimeException("Payment not found"));

        return toPaymentResponseDto(payment);
    }
}
