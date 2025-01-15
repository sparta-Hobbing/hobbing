package com.hobbing.reservation_pay.application.dto;


import com.hobbing.reservation_pay.domain.model.status_enum.SettlementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateSettlePayInfoDto {

    private long totalAmount;
    private SettlementStatus status;
    private String receipt;
    private String transactionPgToken;
}
