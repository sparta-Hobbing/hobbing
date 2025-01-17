package com.hobbing.reservation_pay.domain.client;


import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(access = AccessLevel.PROTECTED)
public class ReserveLectureDto {

    UUID userId;
    UUID lectureScheduleId;


    public static ReserveLectureDto from(Reservation reservation) {
        return ReserveLectureDto.builder()
                .userId(reservation.getUserId())
                .lectureScheduleId(reservation.getLectureScheduleId())
                .build();
    }
}