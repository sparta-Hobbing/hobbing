package com.hobbing.reservation_pay.infrastructure;


import com.hobbing.reservation_pay.common.exception.CommonErrorCode;
import com.hobbing.reservation_pay.common.exception.CustomException;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.infrastructure.dao.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final ReservationJpaRepository jpaRepo;


    @Transactional(readOnly = true)
    public Reservation readReservation(UUID reservationId) {

        return jpaRepo.findById(reservationId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.RESERVATION_NOT_FOUND));
    }
}
