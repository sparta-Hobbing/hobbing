package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.ReservationService;
import com.hobbing.reservation_pay.presentation.dto.GetReservationResBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping("/{id}")
    public ApiResponse<GetReservationResBody> getReservation(@PathVariable UUID id) {

        GetReservationResBody resBody = GetReservationResBody.from(
                reservationService.readReservation(id)
        );

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "예약 조회 성공", resBody
        );
    }
}