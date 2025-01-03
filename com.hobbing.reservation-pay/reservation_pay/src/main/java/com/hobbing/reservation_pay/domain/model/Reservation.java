package com.hobbing.reservation_pay.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private final UUID userId;

    @Column(nullable = false)
    private final String userNickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Payment payment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private UUID lectureScheduleId;

    @Column(nullable = false)
    private String lectureTitle;

    @Column(nullable = false)
    private LocalDateTime lectureScheduleStart;

    @Column(nullable = false)
    private LocalDateTime lectureScheduleEnd;

    @Column(nullable = false)
    private final UUID tutorId;

    @Column(nullable = false)
    private final String tutorNickname;

}
