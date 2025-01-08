package com.hobbing.reservation_pay.domain.client;


import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

public interface LectureServiceClient {

    void cancelLectureReservation(CancelReservedLectureDto dto);

    @Value
    @Builder
    class CancelReservedLectureDto {
        UUID userId;
        UUID studentManagementId;
        UUID lectureScheduleId;
        String lectureTitle;
        UUID tutorId;

        public static CancelReservedLectureDto from(Reservation reservation) {
            return CancelReservedLectureDto.builder()
                    .userId(reservation.getUserId())
                    .studentManagementId(reservation.getStudentManagementId())
                    .lectureScheduleId(reservation.getLectureScheduleId())
                    .lectureTitle(reservation.getLectureTitle())
                    .tutorId(reservation.getTutorId())
                    .build();
        }
    }
}
