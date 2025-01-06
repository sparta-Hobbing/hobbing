package com.hobbing.reservation_pay.domain.model;

import com.hobbing.reservation_pay.domain.model.status_enum.PaymentStatus;
import com.hobbing.reservation_pay.domain.model.status_enum.ReservationStatus;
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
public class Reservation extends BaseEntity {

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


    public void pay(Payment payment) {

        if (this.status == ReservationStatus.PAYED) {
            throw new IllegalStateException("이미 결제된 예약입니다.");
        }
        if (payment.getStatus() != PaymentStatus.PAYED && !payment.getStatus().isTryingToPay()) {
            throw new IllegalStateException("결제 진행중이 아닌 결제 정보입니다.");
        }

        this.payment = payment;
        this.status = ReservationStatus.PAYED;
    }
}
