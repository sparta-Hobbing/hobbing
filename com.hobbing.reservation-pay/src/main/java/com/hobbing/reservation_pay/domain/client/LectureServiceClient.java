package com.hobbing.reservation_pay.domain.client;


public interface LectureServiceClient {

    void cancelReservation(CancelReservedLectureDto dto);

    void reserveLecture(ReserveLectureDto dto);
}
