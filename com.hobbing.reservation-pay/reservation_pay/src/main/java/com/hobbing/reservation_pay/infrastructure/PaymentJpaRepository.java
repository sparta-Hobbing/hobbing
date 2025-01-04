package com.hobbing.reservation_pay.infrastructure;

import com.hobbing.reservation_pay.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {
}
