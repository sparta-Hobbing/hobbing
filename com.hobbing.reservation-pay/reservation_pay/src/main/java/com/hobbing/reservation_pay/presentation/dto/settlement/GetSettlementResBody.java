package com.hobbing.reservation_pay.presentation.dto.settlement;


import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.domain.model.status_enum.SettlementStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class GetSettlementResBody {
    UUID id;
    UUID tutorId;
    UUID lectureId;
    String lectureTitle;
    long totalAmount;
    long commission;
    long tutorAmount;
    SettlementStatus status;
    String receipt;
    String transactionPgToken;
    LocalDateTime createdAt;
    UUID createdBy;
    LocalDateTime updatedAt;
    UUID updatedBy;


    public static GetSettlementResBody from(Settlement settlement) {

        return GetSettlementResBody.builder()
                .id(settlement.getId())
                .tutorId(settlement.getTutorId())
                .lectureId(settlement.getLectureId())
                .lectureTitle(settlement.getLectureTitle())
                .totalAmount(settlement.getTotalAmount())
                .commission(settlement.getCommission())
                .tutorAmount(settlement.getTutorAmount())
                .status(settlement.getStatus())
                .receipt(settlement.getReceipt())
                .transactionPgToken(settlement.getTransactionPgToken())
                .createdAt(settlement.getCreatedAt())
                .createdBy(settlement.getCreatedBy())
                .updatedAt(settlement.getUpdatedAt())
                .updatedBy(settlement.getUpdatedBy())
                .build();
    }
}
