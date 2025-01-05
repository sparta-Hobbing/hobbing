package com.hobbing.reservation_pay.infrastructure;

import com.hobbing.reservation_pay.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {
}
