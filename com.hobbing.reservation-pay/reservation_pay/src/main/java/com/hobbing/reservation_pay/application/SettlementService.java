package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.application.dto.CreateSettlementDto;
import com.hobbing.reservation_pay.application.dto.UpdateSettlePayInfoDto;
import com.hobbing.reservation_pay.domain.CommissionPolicy;
import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.infrastructure.SettlementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Transactional
    public List<Settlement> createSettlements(List<CreateSettlementDto> dtoList) {

        dtoList.forEach(dto -> dto.setCommission(
                commissionPolicy.calculateCommission(dto.getTotalAmount())
        ));

        List<Settlement> createdSettlements
                = dtoList.stream()
                .map(this::createSettlement)
                .toList();

        return createdSettlements;
    }

    public Settlement createSettlement(CreateSettlementDto dto) {

        return settlementRepo.createSettlement(dto);
    }
}
