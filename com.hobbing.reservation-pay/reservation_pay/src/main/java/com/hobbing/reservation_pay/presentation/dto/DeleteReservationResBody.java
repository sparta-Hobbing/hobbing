package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value@Builder
public class DeleteReservationResBody {

    UUID paymentId;
    UUID studentManagementId;
    UUID lectureScheduleId;


    public static DeleteReservationResBody from(Reservation deleted) {
        return DeleteReservationResBody.builder()
                .paymentId(deleted.getPayment().getId())
                .studentManagementId(deleted.getStudentManagementId())
                .lectureScheduleId(deleted.getLectureScheduleId())
                .build();
    }
}
