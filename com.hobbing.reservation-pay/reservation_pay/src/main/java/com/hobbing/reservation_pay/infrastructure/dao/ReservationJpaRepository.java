package com.hobbing.reservation_pay.infrastructure.dao;

import com.hobbing.reservation_pay.domain.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findTop100ByCreatedAtBetweenAndIsDeleted(
            LocalDateTime startCreatedAt, LocalDateTime endCreatedAt,
            boolean isDeleted
    );

    Optional<Reservation> findByUserIdAndLectureScheduleId(
            UUID userId, UUID lectureScheduleId
    );

    Page<Reservation> findByCreatedAtBetweenAndIsDeleted(
            LocalDateTime reservedAfter, LocalDateTime reservedBefore,
            PageRequest pageRequest,
            boolean isDeleted
    );
}
