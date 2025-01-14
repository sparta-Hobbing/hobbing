package com.hobbing.reservation_pay.domain;


import com.hobbing.reservation_pay.domain.client.CancelReservedLectureDto;
import com.hobbing.reservation_pay.domain.client.LectureServiceClient;
import com.hobbing.reservation_pay.domain.client.ReserveLectureDto;
import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationEventHandler {

    private final LectureServiceClient lectureServiceClient;

    public void handleCanceled(Reservation cancelTarget) {

        lectureServiceClient.cancelReservation(
                CancelReservedLectureDto.from(cancelTarget)
        );
    }

    public void handleReserved(Reservation reservation) {

        lectureServiceClient.reserveLecture(
                ReserveLectureDto.from(reservation)
        );
    }
}
