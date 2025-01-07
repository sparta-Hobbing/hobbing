package com.hobbing.reservation_pay.presentation.dto;

import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.domain.model.status_enum.SettlementStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class SearchedSettlementsRes {

    UUID id;
    UUID lectureId;
    String lectureTitle;
    long totalAmount;
    long commission;
    long tutorAmount;
    SettlementStatus status;
    LocalDateTime createdAt;
    UUID createdBy;
    LocalDateTime updatedAt;
    UUID updatedBy;

    public static SearchedSettlementsRes from(Settlement settlement) {
        return SearchedSettlementsRes.builder()
                .id(settlement.getId())
                .lectureId(settlement.getLectureId())
                .lectureTitle(settlement.getLectureTitle())
                .totalAmount(settlement.getTotalAmount())
                .commission(settlement.getCommission())
                .tutorAmount(settlement.getTutorAmount())
                .status(settlement.getStatus())
                .createdAt(settlement.getCreatedAt())
                .createdBy(settlement.getCreatedBy())
                .updatedAt(settlement.getUpdatedAt())
                .updatedBy(settlement.getUpdatedBy())
                .build();
    }
}
