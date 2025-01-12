package com.hobbing.reservation_pay.domain.repository;


import com.hobbing.reservation_pay.domain.model.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
}
