package com.hobbing.reservation_pay.application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Builder
public class SearchReservationsDto {

    private LocalDateTime reservedAfter;
    private LocalDateTime reservedBefore;
    private PageRequest pageRequest;
}
