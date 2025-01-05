package com.hobbing.reservation_pay.presentation.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.hobbing.reservation_pay.application.CreatePaymentDto;
import com.hobbing.reservation_pay.domain.model.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
public class PostPaymentReqBody {
    UUID reservationId;
    UUID userId;
    UUID couponId;
    String couponName;
    String receipt;
    PaymentStatus paymentStatus;
    int payedPrice;
    String transactionPgToken;
    Integer discountedPrice;

    public CreatePaymentDto toDto() {
        return CreatePaymentDto.builder()
                .reservationId(reservationId)
                .userId(userId)
                .couponId(couponId)
                .couponName(couponName)
                .receipt(receipt)
                .paymentStatus(paymentStatus)
                .payedPrice(payedPrice)
                .transactionPgToken(transactionPgToken)
                .discountedPrice(discountedPrice)
                .build();
    }

    @JsonCreator
    @Builder
    public PostPaymentReqBody(UUID reservationId,
                              UUID userId,
                              UUID couponId,
                              String couponName,
                              String receipt,
                              String paymentStatus,
                              int payedPrice,
                              String transactionPgToken,
                              Integer discountedPrice
    ) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.couponId = couponId;
        this.couponName = couponName;
        this.receipt = receipt;
        this.paymentStatus = PaymentStatus.valueOf(paymentStatus);
        this.payedPrice = payedPrice;
        this.transactionPgToken = transactionPgToken;
        this.discountedPrice = discountedPrice;
    }
}
