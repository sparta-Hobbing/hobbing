package com.hobbing.reservation_pay.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_settlement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID tutorId;

    @Column(nullable = false)
    private UUID lectureId;

    @Column(nullable = false)
    private String lectureTitle;

    @Column(nullable = false)
    private Long totalAmount;

    @Column(nullable = false)
    private Long commission;

    @Column(nullable = false)
    private SettlementStatus status;

    @Column(columnDefinition = "TEXT")
    private String receipt;

    @Column(nullable = false)
    private String transactionPgToken;

}
