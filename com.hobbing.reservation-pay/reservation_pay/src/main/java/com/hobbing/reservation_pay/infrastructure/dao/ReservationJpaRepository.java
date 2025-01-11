package com.hobbing.reservation_pay.infrastructure.dao;

import com.hobbing.reservation_pay.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findTop100ByCreatedAtBetween(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt);
}
