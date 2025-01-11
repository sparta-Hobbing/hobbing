package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.domain.ReservationDomainService;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.infrastructure.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationDomainService reservationDomainService;

    private final ReservationRepository reservationRepo;


    public Reservation readReservation(UUID id) {

        return reservationRepo.readReservation(id);
    }

    @Transactional
    public Reservation cancelReservation(UUID reservationId) {

        Reservation cancelTarget = reservationRepo.readReservation(reservationId);
        reservationDomainService.cancel(cancelTarget);

        return cancelTarget;
    }

    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional
    public void cancelExpiredReservation() {

        LocalDateTime cursor = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime lastOfToday = LocalDateTime.now().with(LocalTime.MAX);

        for (List<Reservation> reservations
             = reservationRepo.searchTop100Reservations(cursor, lastOfToday);

             !reservations.isEmpty();

             cursor = reservations.get(reservations.size() - 1).getCreatedAt()
                     , reservations = reservationRepo.searchTop100Reservations(cursor, lastOfToday)
        ) {

            List<Reservation> expireds
                    = reservations.stream()
                    .filter(Reservation::isOverDueDate)
                    .toList();

            expireds.forEach(reservationDomainService::cancel);


            StringBuilder toLog = new StringBuilder();
            toLog.append("Canceled Reservations number: ").append(expireds.size()).append("\n");
            toLog.append("Canceled Reservation ids: \n");
            expireds.forEach(reservation -> toLog.append(reservation.getId()).append("\n"));

            log.info(toLog.toString());
        }
    }
}