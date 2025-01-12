package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.ReservationService;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping("/{id}")
    public ApiResponse<GetReservationResBody> getReservation(@PathVariable UUID id) {

        Reservation reservation = reservationService.readReservation(id);

        return ApiResponse.ofSuccess(
                HttpStatus.OK,
                "예약 조회 성공",
                GetReservationResBody.from(reservation)
        );
    }

    @GetMapping
    public ApiResponse<PagedModel<SearchedReservationRes>> searchReservations(
            @Valid @ModelAttribute PageInfo pageInfo,
            @Valid @ModelAttribute SearchReservationsReqParams params
    ) {

        Page<SearchedReservationRes> searched
                = reservationService.searchReservations(params.toDto(pageInfo))
                .map(SearchedReservationRes::from);

        PagedModel<SearchedReservationRes> resBody
                = new PagedModel<>(searched);

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "OK", resBody
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<DeleteReservationResBody> deleteReservation(@PathVariable UUID id) {

        Reservation deleted = reservationService.cancelReservation(id);

        return ApiResponse.ofSuccess(
                HttpStatus.OK,
                "예약 삭제 성공",
                DeleteReservationResBody.from(deleted)
        );
    }

    @PostMapping
    public ApiResponse<UUID> createReservation(
            @RequestBody PostReservationReqBody reqBody
    ) {

        Reservation created
                = reservationService.createReservation(reqBody.toDto());

        return ApiResponse.ofSuccess(
                HttpStatus.CREATED,
                "예약 생성 성공",
                created.getId()
        );
    }
}