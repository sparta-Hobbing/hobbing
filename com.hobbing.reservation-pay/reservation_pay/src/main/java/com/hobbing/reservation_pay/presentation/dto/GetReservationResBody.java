package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.domain.model.status_enum.PaymentStatus;
import com.hobbing.reservation_pay.domain.model.status_enum.ReservationStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class GetReservationResBody {

    PaymentResBody payment;
    ReservationStatus reservationStatus;
    UUID lectureScheduleId;
    String lectureTitle;
    LocalDateTime lectureScheduleStart;
    LocalDateTime lectureScheduleEnd;
    UUID tutorId;
    String tutorNickname;
    LocalDateTime createdAt;
    UUID createdBy;
    LocalDateTime updatedAt;
    UUID updatedBy;


    public static GetReservationResBody from(Reservation reservation) {

        return GetReservationResBody.builder()
                .payment(PaymentResBody.from(reservation.getPayment()))
                .reservationStatus(reservation.getStatus())
                .lectureScheduleId(reservation.getLectureScheduleId())
                .lectureTitle(reservation.getLectureTitle())
                .lectureScheduleStart(reservation.getLectureScheduleStart())
                .lectureScheduleEnd(reservation.getLectureScheduleEnd())
                .tutorId(reservation.getTutorId())
                .tutorNickname(reservation.getTutorNickname())
                .createdAt(reservation.getCreatedAt())
                .createdBy(reservation.getCreatedBy())
                .updatedAt(reservation.getUpdatedAt())
                .updatedBy(reservation.getUpdatedBy())
                .build();
    }

    @Value
    @Builder
    public static class PaymentResBody {

        UUID id;
        UUID couponId;
        String couponName;
        PaymentStatus status;
        int payedPrice;
        String transactionPgToken;
        int discountedPrice;

        public static PaymentResBody from(Payment payment) {
            return PaymentResBody.builder()
                    .id(payment.getId())
                    .couponId(payment.getCouponId())
                    .couponName(payment.getCouponName())
                    .status(payment.getStatus())
                    .payedPrice(payment.getPayedPrice())
                    .transactionPgToken(payment.getTransactionPgToken())
                    .discountedPrice(payment.getDiscountedPrice())
                    .build();
        }
    }
}
