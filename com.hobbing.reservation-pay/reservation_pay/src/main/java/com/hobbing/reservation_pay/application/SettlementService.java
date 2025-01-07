package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.application.dto.UpdateSettlePayInfoDto;
import com.hobbing.reservation_pay.domain.CommissionPolicy;
import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.infrastructure.SettlementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepo;
    private final CommissionPolicy commissionPolicy;


    public Settlement readSettlement(UUID settlementId) {

        return settlementRepo.readSettlement(settlementId);
    }

    @Transactional
    public void updateSettlementPayInfo(UUID settlementId, UpdateSettlePayInfoDto dto) {

        Settlement settlement = settlementRepo.readSettlement(settlementId);
        long commission = commissionPolicy.calculateCommission(dto.getTotalAmount());

        settlement.updatePayInfo(
                dto.getTotalAmount(),
                commission,
                dto.getStatus(),
                dto.getReceipt(),
                dto.getTransactionPgToken());
    }

}
