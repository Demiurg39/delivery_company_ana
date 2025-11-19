package com.anateam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anateam.dto.PaymentRequestDto;
import com.anateam.dto.PaymentResponseDto;
import com.anateam.dto.PaymentUpdateDto;
import com.anateam.entity.User;
import com.anateam.repository.UserRepository;
import com.anateam.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Initiate a payment", description = "Process a payment for a specific order.")
    @ApiResponse(responseCode = "200", description = "Payment processed successfully")
    public ResponseEntity<PaymentResponseDto> createPayment(@Valid @RequestBody PaymentRequestDto requestDto, Integer courierId) {
        PaymentResponseDto paymentResponse = paymentService.createPayment(requestDto, courierId);
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment status", description = "Check the status of a specific payment transaction.")
    @ApiResponse(responseCode = "200", description = "Payment details found")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    public ResponseEntity<PaymentResponseDto> getPayment(@PathVariable Integer orderId) {
        PaymentResponseDto responseDto = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<PaymentResponseDto> updatePayment(@Valid @RequestBody PaymentUpdateDto updateDto) {
        // TODO: Получить аутентифицированного клиента и проверить, что это его заказ
        PaymentResponseDto paymentResponse = paymentService.updatePayment(updateDto);
        return new ResponseEntity<>( paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<PaymentResponseDto>
    getPaymentForOrder(@PathVariable Integer orderId) {
        PaymentResponseDto paymentResponse =
            paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(paymentResponse);
    }


    private User getAppUserFromUserDetails(UserDetails userDetails) {
        String phoneNumber = userDetails.getUsername();
        return userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow( () -> new RuntimeException("Аутентифицированный пользователь не найден в БД"));
    }
}
