package com.hobbing.reservation_pay.infrastructure;

import com.hobbing.reservation_pay.application.dto.CreateSettlementDto;
import com.hobbing.reservation_pay.application.dto.SearchSettlementsDto;
import com.hobbing.reservation_pay.common.exception.CommonErrorCode;
import com.hobbing.reservation_pay.common.exception.CustomException;
import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.infrastructure.dao.SettlementJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class SettlementRepoInfra {

    private final SettlementJpaRepository jpaRepo;


    public Settlement readSettlement(UUID settlementId) {
        return jpaRepo.findById(settlementId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.SETTLEMENT_NOT_FOUND));
    }

    public Settlement createSettlement(CreateSettlementDto dto) {

        Settlement settlement
                = Settlement.builder()
                .tutorId(dto.getTutorId())
                .lectureId(dto.getLectureId())
                .lectureTitle(dto.getLectureTitle())
                .totalAmount(dto.getTotalAmount())
                .commission(dto.getCommission())
                .status(dto.getStatus())
                .receipt(dto.getReceipt())
                .transactionPgToken(dto.getTransactionPgToken())
                .build();

        return jpaRepo.save(settlement);
    }

    public Page<Settlement> searchSettlements(SearchSettlementsDto dto) {

        Page<Settlement> searched
                = jpaRepo.findByCreatedAtBetweenAndIsDeleted(
                dto.getSettledAfter(),
                dto.getSettledBefore(),
                dto.getPageRequest(),
                false
        );
        if (searched.isEmpty()) {
            throw new CustomException(CommonErrorCode.SETTLEMENT_NOT_FOUND);
        }

        return searched;
    }
}
