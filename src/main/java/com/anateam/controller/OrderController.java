package com.anateam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Order Management", description = "Operations for creating, tracking, and managing delivery orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Create a new order", description = "Allows an authenticated customer to place a new delivery order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation data error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden (if user is not authorized)")
    })
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderCreationDto creationDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponseDto authenticatedCustomer = getDtoFromUserDetails(userDetails);
        OrderResponseDto createdOrder = orderService.createOrder(creationDto, authenticatedCustomer);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @Operation(summary = "Get order by ID", description = "Retrieves detailed information about a specific order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Integer id) {
        OrderResponseDto orderDto = orderService.findOrderDtoById(id);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders", description = "Retrieves a paginated list of all orders.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of orders retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required")
    })
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @PostMapping("/{id}/accept")
    @PreAuthorize("hasRole('COURIER')")
    @Operation(summary = "Accept an order", description = "Allows a courier to accept a pending order.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Заказ принят"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "409", description = "Заказ уже занят другим курьером")
    })
    public ResponseEntity<OrderResponseDto> acceptOrder(
        @PathVariable Integer id,
        @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponseDto authenticatedCourier = getDtoFromUserDetails(userDetails);
        OrderResponseDto acceptedOrder = orderService.acceptOrder(id, authenticatedCourier.id());
        return ResponseEntity.ok(acceptedOrder);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update order status", description = "Allows a courier to update the status of an assigned order.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status or transition"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Not assigned courier"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponseDto>
    updateOrderStatus(@PathVariable Integer id,
                      @Valid @RequestBody OrderStatusUpdateDto statusUpdateDto,
                      @AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDto authenticatedCourier = getDtoFromUserDetails(userDetails);
        OrderResponseDto updatedOrder = orderService.updateOrderStatus(
            authenticatedCourier.id(),
            statusUpdateDto,
            authenticatedCourier
        );
        return ResponseEntity.ok(updatedOrder);
    }

    private User getAppUserFromUserDetails(UserDetails userDetails) {
        String phoneNumber = userDetails.getUsername();
        return userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserResponseDto getDtoFromUserDetails(UserDetails userDetails) {
        User user = getAppUserFromUserDetails(userDetails);
        return new UserResponseDto(
            user.getId(),
            user.getFullName(),
            user.getPhoneNumber(),
            user.getRole().name(),
            user.getCreatedAt().toString());
    }
}
