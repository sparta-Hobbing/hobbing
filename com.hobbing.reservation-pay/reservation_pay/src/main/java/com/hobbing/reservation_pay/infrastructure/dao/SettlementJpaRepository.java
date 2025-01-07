package com.hobbing.reservation_pay.infrastructure.dao;

import com.hobbing.reservation_pay.domain.model.Settlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;


public interface SettlementJpaRepository extends JpaRepository<Settlement, UUID> {
    Page<Settlement> findByCreatedAtBetween(LocalDateTime settledAfter, LocalDateTime settledBefore, Pageable pageRequest);
}
