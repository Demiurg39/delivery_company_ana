package com.anateam.service.impl;

import com.anateam.dto.*;
import com.anateam.entity.*;
import com.anateam.repository.*;
import com.anateam.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    @Override
    public OrderResponseDto createOrder(OrderCreationDto creationDto, UserResponseDto userDto) {
        Order order = new Order();
        order.setPickupAddress(creationDto.pickupAddress());
        order.setDeliveryAddress(creationDto.deliveryAddress());
        order.setPickupCoordinates(creationDto.pickupCoordinates().toString());
        order.setDeliveryCoordinates(creationDto.deliveryCoordinates().toString());
        order.setDescription(creationDto.description());
        order.setStatus(OrderStatus.CREATED);

        orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public OrderResponseDto acceptOrder(Integer orderId, Integer courierId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("The courier was not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("The order has already been taken or completed");
        }

        order.setCourierId(courierId);
        order.setStatus(OrderStatus.ACCEPTED);
        orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Integer orderId, OrderStatusUpdateDto statusUpdateDto, UserResponseDto authenticatedCourier) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getCourierId().equals(authenticatedCourier.id())) {
            throw new RuntimeException("You can't update the status of someone else's order.");
        }

        OrderStatus newStatus = OrderStatus.valueOf(statusUpdateDto.status());
        order.setStatus(newStatus);
        orderRepository.save(order);

        return toDto(order);
    }

    @Override
    public OrderResponseDto findOrderDtoById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return toDto(order);
    }

    @Override
    public Page<OrderResponseDto> findOrdersByCustomerId(Integer customerId, Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::toDto)
                .filter(orderDto -> orderDto.customerId().equals(customerId));
    }

    @Override
    public Page<OrderResponseDto> findAvailableOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::toDto)
                .filter(orderDto -> orderDto.status().equals("CREATED"));
    }

    private OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getCustomerId(),
                order.getCourierId(),
                order.getStatus().toString(),
                order.getPickupAddress(),
                order.getDeliveryAddress(),
                null,
                null,
                order.getPrice(),
                order.getEstimatedMinutes(),
                order.getCreatedAt().toString()
        );
    }
}
