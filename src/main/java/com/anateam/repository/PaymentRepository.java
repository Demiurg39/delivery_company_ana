package com.anateam.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anateam.entity.PaymentStatus;
import com.anateam.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByOrderId(Integer id);

    List<Payment> findAllByStatusAndCreatedAtAfter(PaymentStatus status, OffsetDateTime date);

    List<Payment> findAllByStatusAndCreatedAtBefore(PaymentStatus status, OffsetDateTime date);
}
