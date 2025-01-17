package com.hobbing.reservation_pay.application;

import com.hobbing.reservation_pay.domain.ReservationDomainService;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.infrastructure.ReservationRepoInfra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepoInfra reservationRepository;

    @Mock
    private ReservationDomainService domainService;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCancelExpiredReservation() {
        // 준비
        LocalDateTime now = LocalDateTime.now();

        Reservation expiredReservation = mock(Reservation.class);
        LocalDateTime expiredDate = now.minusDays(Reservation.PAYMENT_DURATION_DAYS + 1);
        UUID expiredId = UUID.fromString("f1090e34-d310-456a-aea2-c7c379efe49f");
        when(expiredReservation.isOverDueDate()).thenReturn(true);
        when(expiredReservation.getCreatedAt()).thenReturn(expiredDate);
        when(expiredReservation.getId()).thenReturn(expiredId);

        Reservation validReservation = mock(Reservation.class);
        LocalDateTime validDate = now.minusDays(Reservation.PAYMENT_DURATION_DAYS - 1);
        UUID validId = UUID.fromString("33cc1241-7d3a-42cc-84c8-e2d233a90773");
        when(validReservation.isOverDueDate()).thenReturn(false);
        when(validReservation.getCreatedAt()).thenReturn(validDate);
        when(validReservation.getId()).thenReturn(validId);

        when(reservationRepository.searchTop100Reservations(
                any(LocalDateTime.class), any(LocalDateTime.class)
        ))
                .thenReturn(Arrays.asList(expiredReservation, validReservation))
                .thenReturn(List.of()); // 두 번째 호출에서는 빈 리스트 반환

        // 실행
        reservationService.cancelExpiredReservation();

        // 검증
        verify(domainService, times(1)).cancel(expiredReservation); // 도메인 서비스 호출 검증
        verify(domainService, never()).cancel(validReservation);   // 유효한 예약은 호출되지 않음
    }
}


