package com.hobbing.reservation_pay.presentation.dto.settlement;


import com.hobbing.reservation_pay.application.dto.UpdateSettlePayInfoDto;
import com.hobbing.reservation_pay.domain.model.status_enum.SettlementStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PutSettleReqBody {

    long totalAmount;
    SettlementStatus status;
    String receipt;
    String transactionPgToken;


    public UpdateSettlePayInfoDto toDto() {

        return UpdateSettlePayInfoDto.builder()
                .totalAmount(totalAmount)
                .status(status)
                .receipt(receipt)
                .transactionPgToken(transactionPgToken)
                .build();
    }
}
