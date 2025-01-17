package com.hobbing.reservation_pay.presentation.dto.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hobbing.reservation_pay.application.dto.UpdatePaymentDto;
import com.hobbing.reservation_pay.domain.model.status_enum.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;


@Value
public class PutPaymentReqBody {
    UUID reservationId;
    String receipt;
    PaymentStatus paymentStatus;
    int payedPrice;
    String transactionPgToken;

    @JsonCreator
    @Builder
    public PutPaymentReqBody(UUID reservationId,
                             String receipt,
                             String paymentStatus,
                             int payedPrice,
                             String transactionPgToken
    ) {
        this.reservationId = reservationId;
        this.receipt = receipt;
        this.paymentStatus = PaymentStatus.valueOf(paymentStatus);
        this.payedPrice = payedPrice;
        this.transactionPgToken = transactionPgToken;
    }


    public UpdatePaymentDto toDto() {
        return UpdatePaymentDto.builder()
                .reservationId(reservationId)
                .receipt(receipt)
                .paymentStatus(paymentStatus)
                .payedPrice(payedPrice)
                .transactionPgToken(transactionPgToken)
                .build();
    }

}

