package com.hobbing.reservation_pay.domain;


import com.hobbing.reservation_pay.domain.client.LectureServiceClient;
import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hobbing.reservation_pay.domain.client.LectureServiceClient.*;


@Service
@RequiredArgsConstructor
public class ReservationEventHandler {

    private final LectureServiceClient lectureServiceClient;

    public void handleCanceled(Reservation cancelTarget) {

        lectureServiceClient.cancelLectureReservation(
                CancelReservedLectureDto.from(cancelTarget)
        );
    }
}
