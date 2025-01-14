package com.hobbing.reservation_pay.presentation.dto.reservation;


import com.hobbing.reservation_pay.application.dto.SearchReservationsDto;
import com.hobbing.reservation_pay.presentation.dto.PageInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Value
public class SearchReservationsReqParams {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime reservedAfter;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime reservedBefore;


    public SearchReservationsDto toDto(PageInfo pageInfoParams) {

        return SearchReservationsDto.builder()
                .reservedAfter(reservedAfter)
                .reservedBefore(reservedBefore)
                .pageRequest(pageInfoParams.toPageRequest())
                .build();
    }
}