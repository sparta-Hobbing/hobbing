package com.hobbing.reservation_pay.infrastructure;

import com.hobbing.reservation_pay.common.exception.CommonErrorCode;
import com.hobbing.reservation_pay.common.exception.CustomException;
import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.infrastructure.dao.SettlementJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class SettlementRepository {

    private final SettlementJpaRepository jpaRepo;


    public Settlement readSettlement(UUID settlementId) {
        return jpaRepo.findById(settlementId)
                .orElseThrow(()->new CustomException(CommonErrorCode.SETTLEMENT_NOT_FOUND));
    }
}
