package com.anateam.repository;

import com.anateam.entity.Order;
import com.anateam.entity.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(Integer id);

    List<Order> findByCourierId(Integer id);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findAllByAddressContaining(String addressPart);
}
