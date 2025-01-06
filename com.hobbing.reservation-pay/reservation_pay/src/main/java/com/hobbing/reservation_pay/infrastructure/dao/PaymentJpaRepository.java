package com.hobbing.reservation_pay.infrastructure.dao;

import com.hobbing.reservation_pay.domain.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {

    Page<Payment> findByCreatedAtBetween(
            LocalDateTime createdAfter, LocalDateTime createdBefore, Pageable pageRequest
    );

}
