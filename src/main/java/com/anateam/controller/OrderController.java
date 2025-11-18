package com.anateam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anateam.dto.OrderCreationDto;
import com.anateam.dto.OrderResponseDto;
import com.anateam.dto.OrderStatusUpdateDto;
import com.anateam.dto.UserResponseDto;
import com.anateam.entity.User;
import com.anateam.repository.OrderRepository;
import com.anateam.repository.UserRepository;
import com.anateam.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Operations for creating, tracking, and managing delivery orders")
@SecurityRequirement(name = "bearerAuth") // Указывает, что эти методы требуют JWT
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Create a new order", description = "Allows an authenticated customer to place a new delivery order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "403", description = "Forbidden (if user is not authorized)")
    })
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderCreationDto creationDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails // Скрываем userDetails из Swagger
    ) {
        UserResponseDto authenticatedCustomer = getDtoFromUserDetails(userDetails);
        OrderResponseDto createdOrder = orderService.createOrder(creationDto, authenticatedCustomer);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @Operation(summary = "Get order by ID", description = "Retrieves detailed information about a specific order.")
    @ApiResponse(responseCode = "200", description = "Order found")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Integer id) {
        OrderResponseDto orderDto = orderService.findOrderDtoById(id);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/{id}/accept")
    @Operation(summary = "Accept an order", description = "Allows a courier to accept a pending order.")
    public ResponseEntity<OrderResponseDto> acceptOrder(
        @PathVariable Integer id,
        @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponseDto authenticatedCourier = getDtoFromUserDetails(userDetails);
        OrderResponseDto acceptedOrder = orderService.acceptOrder(id, authenticatedCourier.id());
        return ResponseEntity.ok(acceptedOrder);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto>
    updateOrderStatus(@PathVariable Integer id,
                      @Valid @RequestBody OrderStatusUpdateDto statusUpdateDto,
                      @AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDto authenticatedCourier =
            getDtoFromUserDetails(userDetails);
        OrderResponseDto updatedOrder = orderService.updateOrderStatus(
            authenticatedCourier.id(), statusUpdateDto, authenticatedCourier);
        return ResponseEntity.ok(updatedOrder);
    }

    private User getAppUserFromUserDetails(UserDetails userDetails) {
        String phoneNumber = userDetails.getUsername();
        return userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(
                ()
                    -> new RuntimeException(
                        "Аутентифицированный пользователь не найден в БД"));
    }

    private UserResponseDto getDtoFromUserDetails(UserDetails userDetails) {
        User user = getAppUserFromUserDetails(userDetails);
        return new UserResponseDto(user.getId(), user.getFullName(),
                                   user.getPhoneNumber(), user.getRole().name(),
                                   user.getCreatedAt().toString());
    }
}
