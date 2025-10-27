package com.anateam.controller;

import com.anateam.dto.OrderCreationDto;
import com.anateam.dto.OrderResponseDto;
import com.anateam.dto.OrderStatusUpdateDto;
import com.anateam.service.OrderService;
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
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto>
    createOrder(@Valid @RequestBody OrderCreationDto creationDto) {
        // TODO: get authenticated customer
        // OrderResponseDto createdOrder = orderService.createOrder(creationDto,
        // authenticatedCustomer);
        return new ResponseEntity<>(/*createOrder, */ HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto>
    getOrderById(@PathVariable Integer id) {
        OrderResponseDto orderDto = orderService.findOrderDtoById(id);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<OrderResponseDto>
    acceptOrder(@PathVariable Integer id) {
        // TODO: get authenticated courier
        // OrderResponseDto acceptedOrder = orderService.acceptOrder(id,
        // authenticatedCourier);
        return ResponseEntity.ok(/* acceptedOrder */ null);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto>
    updateOrderStatus(@PathVariable Integer id, @Valid @RequestBody OrderStatusUpdateDto statusUpdateDto) {
        // TODO: get authenticated courier
        // OrderResponseDto updatedOrder = orderService.updateOrder(id, statusUpdateDto,
        // authenticatedCourier);
        return ResponseEntity.ok(/* acceptedOrder */ null);
    }

}
