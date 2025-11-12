package com.anateam.service;

import com.anateam.dto.GpsCoordinatesDto;
import com.anateam.dto.OrderCreationDto;
import com.anateam.dto.OrderResponseDto;
import com.anateam.dto.OrderStatusUpdateDto;
import com.anateam.dto.UserResponseDto;
import com.anateam.entity.Courier;
import com.anateam.entity.GpsCoordinates;
import com.anateam.entity.Order;
import com.anateam.entity.OrderStatus;
import com.anateam.repository.CourierRepository;
import com.anateam.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    @Override
    public OrderResponseDto createOrder(OrderCreationDto creationDto,
                                        UserResponseDto userDto) {
        Order order = new Order();
        order.setPickupAddress(creationDto.pickupAddress());
        order.setDeliveryAddress(creationDto.deliveryAddress());
        order.setPickupCoordinates(creationDto.pickupCoordinates());
        order.setDeliveryCoordinates(creationDto.deliveryCoordinates());
        order.setDescription(creationDto.description());
        order.setStatus(OrderStatus.NEW);

        orderRepository.save(order);
        return toOrderResponseDto(order);
    }

    @Override
    public OrderResponseDto acceptOrder(Integer orderId, Integer courierId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new RuntimeException("Order not found"));
        Courier courier = courierRepository.findById(courierId).orElseThrow(
            () -> new RuntimeException("The courier was not found"));

        if (order.getStatus() != OrderStatus.NEW) {
            throw new RuntimeException(
                "The order has already been taken or completed");
        }

        order.setCourier(courier);
        order.setStatus(OrderStatus.ASSIGNED);
        orderRepository.save(order);
        return toOrderResponseDto(order);
    }

    @Override
    public OrderResponseDto
    updateOrderStatus(Integer orderId, OrderStatusUpdateDto statusUpdateDto,
                      UserResponseDto authenticatedCourier) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new RuntimeException("Order not found"));

        if (!order.getCourier().getId().equals(authenticatedCourier.id())) {
            throw new RuntimeException(
                "You can't update the status of someone else's order.");
        }

        OrderStatus newStatus = OrderStatus.valueOf(statusUpdateDto.status());
        order.setStatus(newStatus);
        orderRepository.save(order);

        return toOrderResponseDto(order);
    }

    public OrderResponseDto findOrderDtoById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new RuntimeException("Order not found"));
        return toOrderResponseDto(order);
    }

    private OrderResponseDto toOrderResponseDto(Order order) {
        return new OrderResponseDto(
            order.getId(), order.getCustomer().getId(),
            order.getCourier().getId(), order.getStatus().name(),
            order.getPickupAddress(), order.getDeliveryAddress(),
            toGpsCoordinatesDto(order.getPickupCoordinates()),
            toGpsCoordinatesDto(order.getDeliveryCoordinates()),
            order.getPrice(), order.getEstimatedMinutes(),
            order.getCreatedAt().toString());
    }

    private GpsCoordinatesDto toGpsCoordinatesDto(GpsCoordinates coords) {
        return new GpsCoordinatesDto(coords.getLatitude(),
                                     coords.getLongitude());
    }
}
