package com.hobbing.reservation_pay.infrastructure;


import com.hobbing.reservation_pay.domain.model.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final ReservationJpaRepository jpaRepo;


    @Transactional
    public void updatePayment(UUID reservationId, Payment payment) {
        jpaRepo.findById(reservationId)
                .ifPresentOrElse(
                        reservation -> reservation.pay(payment),
                        () -> {
//                    throw new IllegalArgumentException("Reservation not found");//todo exception처리
                        }
                );
    }
}
