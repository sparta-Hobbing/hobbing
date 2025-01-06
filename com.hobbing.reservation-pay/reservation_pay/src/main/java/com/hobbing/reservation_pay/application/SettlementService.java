package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.infrastructure.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepo;


    public Settlement readSettlement(UUID settlementId) {
        return settlementRepo.readSettlement(settlementId);
    }
}
