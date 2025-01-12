package com.hobbing.reservation_pay.domain;


import com.hobbing.reservation_pay.domain.model.MakeReservationDto;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


//todo 지용님 api구현완료시 event handler 주석해제
@Service
@RequiredArgsConstructor
public class ReservationDomainService {

    private final ReservationEventHandler reservationEventHandler;
    private final ReservationRepository reservationRepo;

    public void cancel(Reservation reservation) {

//        reservationEventHandler.handleCanceled(reservation);
        reservation.cancel();
    }

    public Reservation reserve(MakeReservationDto dto) {

        Reservation toSave = Reservation.makeModel(dto);
        Reservation saved = reservationRepo.save(toSave);

//        reservationEventHandler.handleReserved(saved);

        return saved;
    }
}