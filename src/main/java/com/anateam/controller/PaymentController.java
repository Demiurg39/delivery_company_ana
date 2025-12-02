package com.anateam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.anateam.repository.PaymentRepository;
import com.anateam.service.PaymentService;
import com.anateam.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Payments", description = "Operations for processing and managing payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Initiate a payment", description = "Process a payment for a specific order. Requires CUSTOMER role.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Payment processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid payment request or validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User does not have CUSTOMER role")
    })
    public ResponseEntity<PaymentResponseDto> createPayment(
            @Valid @RequestBody PaymentRequestDto requestDto,
            @Parameter(description = "ID of the courier (optional)", required = false) Integer courierId) {
        PaymentResponseDto paymentResponse = paymentService.createPayment(requestDto, courierId);
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get payment status", description = "Check the status of a specific payment transaction by Order ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment details found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentResponseDto> getPayment(@PathVariable Integer orderId) {
        PaymentResponseDto responseDto = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update payment", description = "Updates an existing payment record.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid update request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentResponseDto> updatePayment(@Valid @RequestBody PaymentUpdateDto updateDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!paymentService.isOrderOwnedByUser(updateDto.id(), userDetails.getUsername())) {
             return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        PaymentResponseDto paymentResponse = paymentService.updatePayment(updateDto);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get payment for order", description = "Retrieves payment details associated with a specific order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment details found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Payment not found for the given order")
    })
    public ResponseEntity<PaymentResponseDto>
    getPaymentForOrder(@PathVariable Integer orderId) {
        PaymentResponseDto paymentResponse =
            paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(paymentResponse);
    }


}
