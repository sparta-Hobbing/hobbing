package com.hobbing.reservation_pay.domain.client;

import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(access = AccessLevel.PROTECTED)
public class CancelReservedLectureDto {//todo student테이블 조회관련사항 확실시 되면 삭제or주석해제
    UUID userId;
    //        UUID studentManagementId;
    UUID lectureScheduleId;
    String lectureTitle;
    UUID tutorId;

    public static CancelReservedLectureDto from(Reservation reservation) {
        return CancelReservedLectureDto.builder()
                .userId(reservation.getUserId())
//                    .studentManagementId(reservation.getStudentId())
                .lectureScheduleId(reservation.getLectureScheduleId())
                .lectureTitle(reservation.getLectureTitle())
                .tutorId(reservation.getTutorId())
                .build();
    }
}
