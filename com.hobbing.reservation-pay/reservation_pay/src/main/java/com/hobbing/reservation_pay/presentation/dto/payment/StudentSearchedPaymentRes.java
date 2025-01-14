package com.hobbing.reservation_pay.presentation.dto.payment;


import com.hobbing.reservation_pay.domain.model.Payment;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class StudentSearchedPaymentRes {

    UUID id;
    UUID couponId;
    String couponName;
    int payedPrice;
    String transactionPgToken;
    int discountedPrice;
    LocalDateTime createdAt;
    UUID createdBy;
    LocalDateTime updatedAt;
    UUID updatedBy;

    public static StudentSearchedPaymentRes from(Payment payment) {

        return StudentSearchedPaymentRes.builder()
                .id(payment.getId())
                .couponId(payment.getCouponId())
                .couponName(payment.getCouponName())
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