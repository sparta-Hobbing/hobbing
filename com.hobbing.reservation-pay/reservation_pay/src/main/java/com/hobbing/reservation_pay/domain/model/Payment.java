package com.hobbing.reservation_pay.domain.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "p_payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Payment {

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

}