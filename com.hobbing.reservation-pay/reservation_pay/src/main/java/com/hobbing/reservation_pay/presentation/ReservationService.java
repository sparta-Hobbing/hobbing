package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.infrastructure.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepo;

    public Reservation readReservation(UUID id) {

        return reservationRepo.readReservation(id);
    }
}

