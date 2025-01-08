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
        UUID studentManagementId;
        UUID lectureScheduleId;

        public static CancelReservedLectureDto from(Reservation reservation) {
            return CancelReservedLectureDto.builder()
                    .studentManagementId(reservation.getStudentManagementId())
                    .lectureScheduleId(reservation.getLectureScheduleId())
                    .build();
        }
    }
}
