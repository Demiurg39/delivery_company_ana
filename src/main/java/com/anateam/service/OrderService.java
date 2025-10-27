package com.anateam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anateam.dto.OrderCreationDto;
import com.anateam.dto.OrderResponseDto;
import com.anateam.dto.OrderStatusUpdateDto;
import com.anateam.dto.UserResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(OrderCreationDto creationDto, UserResponseDto userDto);

    OrderResponseDto acceptOrder(Integer orderId, Integer courierId);

    OrderResponseDto updateOrderStatus(Integer orderId, OrderStatusUpdateDto statusUpdateDto, UserResponseDto authenticatedCourier);

    OrderResponseDto findOrderDtoById(Integer orderId);

    Page<OrderResponseDto> findOrdersByCustomerId(Integer customerId, Pageable pageable);

    Page<OrderResponseDto> findAvailableOrders(Pageable pageable);
}
