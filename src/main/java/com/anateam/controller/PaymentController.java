package com.anateam.controller;

import com.anateam.dto.PaymentResponseDto;
import com.anateam.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto>
    createPayment(@Valid @RequestBody PaymentController requestDto) {
        // TODO: Получить аутентифицированного клиента и проверить, что это его
        // заказ PaymentResponseDto paymentResponse =
        // paymentService.createPayment(requestDto, authenticatedCustomer);
        return new ResponseEntity<>(/* paymentResponse,*/ HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PaymentResponseDto>
    updatePayment(@Valid @RequestBody PaymentController requestDto) {
        // TODO: Получить аутентифицированного клиента и проверить, что это его
        // заказ PaymentResponseDto paymentResponse =
        // paymentService.createPayment(requestDto, authenticatedCustomer);
        return new ResponseEntity<>(/* paymentResponse,*/ HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<PaymentResponseDto>
    getPaymentForOrder(@PathVariable Integer orderId) {
        PaymentResponseDto paymentResponse =
            paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(paymentResponse);
    }
}
