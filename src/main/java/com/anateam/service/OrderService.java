package com.anateam.service;

import com.anateam.dto.OrderCreationDto;
import com.anateam.dto.OrderResponseDto;
import com.anateam.dto.OrderStatusUpdateDto;
import com.anateam.dto.UserResponseDto;

public interface OrderService {

    public OrderResponseDto createOrder(OrderCreationDto creationDto,
                                        UserResponseDto userDto);

    public OrderResponseDto findOrderById(Integer id);

    public OrderResponseDto acceptOrder(Integer id, UserResponseDto userDto);

    public OrderResponseDto updateOrder(Integer id,
                                        OrderStatusUpdateDto statusUpdateDto,
                                        UserResponseDto authenticatedCourier);
}
