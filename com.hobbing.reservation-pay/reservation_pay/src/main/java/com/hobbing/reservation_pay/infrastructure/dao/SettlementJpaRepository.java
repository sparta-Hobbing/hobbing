package com.hobbing.reservation_pay.infrastructure.dao;

import com.hobbing.reservation_pay.domain.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface SettlementJpaRepository extends JpaRepository<Settlement, UUID> {
}
