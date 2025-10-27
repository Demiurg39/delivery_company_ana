package com.anateam.repository;

import com.anateam.entity.PaymenStatus;
import com.anateam.entity.Payment;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByOrderId(Integer id);

    List<Payment> findAllByStatusAndCreatedAtAfter(PaymenStatus status, OffsetTime date);

    List<Payment> findAllByStatusAndCreatedAtBefore(PaymenStatus status, OffsetTime date);
}
