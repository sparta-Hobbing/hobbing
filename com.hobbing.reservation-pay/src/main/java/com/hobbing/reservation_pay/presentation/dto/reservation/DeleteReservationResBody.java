package com.hobbing.reservation_pay.presentation.dto.reservation;


import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(access = AccessLevel.PROTECTED) //todo 지용님 수정 완료후 어느 id값 리턴할지 결정
public class DeleteReservationResBody {

    UUID paymentId;
//    UUID studentManagementId;
    UUID userId;
    UUID lectureScheduleId;


    public static DeleteReservationResBody from(Reservation deleted) {
        return DeleteReservationResBody.builder()
                .paymentId(deleted.getPayment().getId())
//                .studentManagementId(deleted.getStudentId())
                .userId(deleted.getUserId())
                .lectureScheduleId(deleted.getLectureScheduleId())
                .build();
    }
}
