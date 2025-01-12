package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.domain.model.status_enum.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(access = AccessLevel.PROTECTED)
public class SearchedReservationRes {

    ReservationStatus status;
    UUID lectureScheduleId;
    String lectureTitle;
    LocalDateTime lectureScheduleStart;
    LocalDateTime lectureScheduleEnd;
    String tutorNickname;
    LocalDateTime createdAt;
    UUID createdBy;
    LocalDateTime updatedAt;
    UUID updatedBy;


    public static SearchedReservationRes from(Reservation reservation) {
        return SearchedReservationRes.builder()
                .status(reservation.getStatus())
                .lectureScheduleId(reservation.getLectureScheduleId())
                .lectureTitle(reservation.getLectureTitle())
                .lectureScheduleStart(reservation.getLectureScheduleStart())
                .lectureScheduleEnd(reservation.getLectureScheduleEnd())
                .tutorNickname(reservation.getTutorNickname())
                .createdAt(reservation.getCreatedAt())
                .createdBy(reservation.getCreatedBy())
                .updatedAt(reservation.getUpdatedAt())
                .updatedBy(reservation.getUpdatedBy())
                .build();
    }
}
