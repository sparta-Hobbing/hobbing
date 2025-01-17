package com.hobbing.reservation_pay.application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;


@AllArgsConstructor
@Builder
@Getter
public class SearchSettlementsDto {

    private LocalDateTime settledAfter;
    private LocalDateTime settledBefore;
    private PageRequest pageRequest;
}
