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
public class UpdatePaymentDto {

    private UUID reservationId;
    private String receipt;
    private final PaymentStatus paymentStatus;
    private int payedPrice;
    private final String transactionPgToken;
}
