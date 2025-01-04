package com.hobbing.reservation_pay.presentation.dto;

import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.domain.model.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class GetPaymentResponse {

    UUID couponId;
    String couponName;
    String receipt;
    PaymentStatus status;
    int payedPrice;
    String transactionPgToken;
    int discountedPrice;
    LocalDateTime createdAt;
    UUID createdBy;
    LocalDateTime updatedAt;
    UUID updatedBy;


    public static GetPaymentResponse from(Payment payment) {
        return GetPaymentResponse.builder()
                .couponId(payment.getCouponId())
                .couponName(payment.getCouponName())
                .receipt(payment.getReceipt())
                .status(payment.getStatus())
                .payedPrice(payment.getPayedPrice())
                .transactionPgToken(payment.getTransactionPgToken())
                .discountedPrice(payment.getDiscountedPrice())
                .createdAt(payment.getCreatedAt())
                .createdBy(payment.getCreatedBy())
                .updatedAt(payment.getUpdatedAt())
                .updatedBy(payment.getUpdatedBy())
                .build();
    }
}

