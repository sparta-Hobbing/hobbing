package com.hobbing.reservation_pay.domain;


import com.hobbing.reservation_pay.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationDomainService {

    public void cancel(Reservation reservation) {

        //todo 지용님 api구현완료시 주석해제
//        reservationEventHandler.handleCanceled(reservation);
        reservation.cancel();
    }

}