package com.anateam.repository;

import com.anateam.dto.OrderResponseDto;
import com.anateam.entity.Order;
import com.anateam.entity.OrderStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(Integer id);

    List<Order> findByCourierId(Integer id);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findAllByPickupAddressContaining(String pickupAddress);

    List<Order> findAllByDeliveryAddressContaining(String pickupAddress);

    List<Order> findAllByPickupAddressContainingOrDeliveryAddressContaining(String text, String textAgain);

    Page<OrderResponseDto> findOrdersByCustomerId(Integer customerId, Pageable pageable);

    Page<OrderResponseDto> findOrdersByStatus(String status, Pageable pageable);
}
