package com.hobbing.reservation_pay.domain.repository;


import com.hobbing.reservation_pay.domain.model.Reservation;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findDuplicate(UUID userId, UUID lectureScheduleId);
}
