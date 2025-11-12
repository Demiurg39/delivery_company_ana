package com.anateam.controller;

import com.anateam.dto.OrderCreationDto;
import com.anateam.dto.OrderResponseDto;
import com.anateam.dto.OrderStatusUpdateDto;
import com.anateam.dto.UserResponseDto;
import com.anateam.entity.User;
import com.anateam.repository.OrderRepository;
import com.anateam.repository.UserRepository;
import com.anateam.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<OrderResponseDto>
    createOrder(@Valid @RequestBody OrderCreationDto creationDto,
                @AuthenticationPrincipal UserDetails userDetails) {
        // NOTE: get authenticated customer
        UserResponseDto authenticatedCustomer =
            getDtoFromUserDetails(userDetails);
        OrderResponseDto createdOrder =
            orderService.createOrder(creationDto, authenticatedCustomer);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto>
    getOrderById(@PathVariable Integer id) {
        OrderResponseDto orderDto = orderRepository.findOrderDtoById(id);
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
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
        @PathVariable Integer id,
        @Valid @RequestBody OrderStatusUpdateDto statusUpdateDto) {
        // TODO: get authenticated courier
        // OrderResponseDto updatedOrder = orderService.updateOrder(id,
        // statusUpdateDto, authenticatedCourier);
        return ResponseEntity.ok(/* acceptedOrder */ null);
    }

    /**
     * Находит нашего User (Entity) по номеру телефона из токена.
     */
    private User getAppUserFromUserDetails(UserDetails userDetails) {
        String phoneNumber = userDetails.getUsername();
        return userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(
                ()
                    -> new RuntimeException(
                        "Аутентифицированный пользователь не найден в БД"));
    }

    /**
     * Конвертирует UserDetails в UserResponseDto (который ожидает сервис).
     */
    private UserResponseDto getDtoFromUserDetails(UserDetails userDetails) {
        User user = getAppUserFromUserDetails(userDetails);
        // (Этот код конвертации у тебя уже есть в AuthServiceImpl)
        return new UserResponseDto(user.getId(), user.getFullName(),
                                   user.getPhoneNumber(), user.getRole().name(),
                                   user.getCreatedAt().toString());
    }
}
