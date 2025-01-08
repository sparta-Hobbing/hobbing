package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.domain.ReservationEventHandler;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.infrastructure.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepo;
    private final ReservationEventHandler reservationEventHandler;


    public Reservation readReservation(UUID id) {

        return reservationRepo.readReservation(id);
    }

    @Transactional
    public Reservation cancelReservation(UUID reservationId) {

        Reservation cancelTarget = reservationRepo.readReservation(reservationId);

        //todo 지용님 api구현완료시 주석해제
//        reservationEventHandler.handleCanceled(cancelTarget);
        cancelTarget.cancel();

        return cancelTarget;
    }
}

