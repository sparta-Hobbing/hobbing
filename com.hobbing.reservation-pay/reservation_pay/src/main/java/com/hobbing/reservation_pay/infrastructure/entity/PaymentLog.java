package com.hobbing.reservation_pay.infrastructure.entity;

import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.domain.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_payment_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class PaymentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private final Payment payment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private String transactionPgToken;

    @Column
    private Integer transactedMoney;

    @Column(columnDefinition = "TEXT")
    private String receipt;
}
