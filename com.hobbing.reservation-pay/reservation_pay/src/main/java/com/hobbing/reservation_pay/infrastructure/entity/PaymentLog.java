package com.hobbing.reservation_pay.infrastructure.entity;

import com.hobbing.reservation_pay.domain.model.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_payment_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PaymentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String transactionPgToken;

    @Column
    private Integer transactedMoney;

    @Column(columnDefinition = "TEXT")
    private String receipt;
}
