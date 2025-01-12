package com.hobbing.reservation_pay.domain.model;

import com.hobbing.reservation_pay.domain.model.status_enum.PaymentStatus;
import com.hobbing.reservation_pay.domain.model.status_enum.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity
@Table(name = "p_reservation",
        indexes = @Index(name = "idx_reservation_created_at", columnList = "created_at"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
public class Reservation extends BaseEntity {

    public static final byte PAYMENT_DURATION_DAYS = 5;


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

    //todo 지용님 말에 따르면 userId, lectureScheduleId 로도 student 테이블 조회 가능, 만약 다르다면 다시 주석해제
//    @Column(nullable = false)
//    private final UUID studentId;


    public void pay(Payment payment) {

        if (this.status == ReservationStatus.RESERVED_PAID) {
            throw new IllegalStateException("이미 결제된 예약입니다.");
        }
        if (payment.getStatus() != PaymentStatus.PAYED
                && !payment.getStatus().isTryingToPay()) {
            throw new IllegalStateException("결제 진행중이 아닌 결제 정보입니다.");
        }
        if (this.isOverDueDate()) {
            throw new IllegalStateException("결제 기간이 만료되었습니다.");
        }

        this.payment = payment;
        this.status = ReservationStatus.RESERVED_PAID;
    }

    public LocalDateTime getPaymentDueDate() {

        return createdAt.toLocalDate().atStartOfDay()
                .plusDays(PAYMENT_DURATION_DAYS);
    }

    public boolean isOverDueDate() {

        return status == ReservationStatus.RESERVED_UNPAID
                && now().isAfter(getPaymentDueDate());
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELED;
    }

    public static Reservation makeModel(MakeReservationDto dto) {

        return Reservation.builder()
                .userId(dto.getUserId())
                .userNickname(dto.getUserNickname())
                .status(ReservationStatus.RESERVED_UNPAID)
                .lectureScheduleId(dto.getLectureScheduleId())
                .lectureTitle(dto.getLectureTitle())
                .lectureScheduleStart(dto.getLectureScheduleStart())
                .lectureScheduleEnd(dto.getLectureScheduleEnd())
                .tutorId(dto.getTutorId())
                .tutorNickname(dto.getTutorNickname())
                .build();
    }
}