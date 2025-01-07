package com.hobbing.reservation_pay.presentation.dto;

import com.hobbing.reservation_pay.application.dto.CreateSettlementDto;
import com.hobbing.reservation_pay.domain.model.status_enum.SettlementStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;


@Value
@Builder
public class PostSettlementReqBody {

    @NotNull
    UUID tutorId;
    @NotNull
    UUID lectureId;
    @NotBlank
    String lectureTitle;
    @NotNull
    @Min(0)
    Long totalAmount;
    @NotNull
    SettlementStatus settlementStatus;
    @NotBlank
    String receipt;
    @NotBlank
    String transactionPgToken;


    public CreateSettlementDto toDto() {

        return CreateSettlementDto.builder()
                .tutorId(tutorId)
                .lectureId(lectureId)
                .lectureTitle(lectureTitle)
                .totalAmount(totalAmount)
                .status(settlementStatus)
                .receipt(receipt)
                .transactionPgToken(transactionPgToken)
                .build();
    }
}