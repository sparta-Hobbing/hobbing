package com.hobbing.reservation_pay.application.dto;

import com.hobbing.reservation_pay.domain.model.status_enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class CreatePaymentDto {

    private final UUID reservationId;
    private final UUID userId;
    private UUID couponId;
    private String couponName;
    private final String receipt;
    private final PaymentStatus paymentStatus;
    private final int payedPrice;
    private String transactionPgToken;
    private Integer discountedPrice;
}
