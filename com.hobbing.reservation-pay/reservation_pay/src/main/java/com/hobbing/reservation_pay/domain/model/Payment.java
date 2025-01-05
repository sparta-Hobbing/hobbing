package com.hobbing.reservation_pay.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "p_payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column
    private UUID couponId;

    @Column
    private String couponName;

    @Column(columnDefinition = "text")
    private String receipt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private int payedPrice;

    @Column(nullable = false)
    private String transactionPgToken;

    @Column(nullable = false)
    private int discountedPrice;


    public boolean isTryingToPay() {

        return status == PaymentStatus.PAY_WAIT
                || status == PaymentStatus.PAY_PG_API_ERROR
                || status == PaymentStatus.PG_DENIED_PAY
                || status == PaymentStatus.PAYED;
    }

    public boolean isTryingToCancel() {

        return status == PaymentStatus.CANCEL_WAIT
                || status == PaymentStatus.CANCEL_PG_API_ERROR
                || status == PaymentStatus.PG_DENIED_CANCEL
                || status == PaymentStatus.CANCELED;
    }

}