package com.hobbing.reservation_pay.domain;


import com.hobbing.reservation_pay.domain.model.MakeReservationDto;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

        Optional<Reservation> duplicateReservation
                = reservationRepo.findDuplicate(dto.getUserId(), dto.getLectureScheduleId());

        duplicateReservation.ifPresent(Void -> {
            throw new IllegalArgumentException("이미 예약한 강의 스케줄입니다.");
        });

        Reservation toSave = Reservation.makeModel(dto);
        Reservation saved = reservationRepo.save(toSave);

//        reservationEventHandler.handleReserved(saved);

        return saved;
    }
}