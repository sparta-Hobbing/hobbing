package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.application.dto.SearchSettlementsDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SearchSettlementsReqParams {

    @NotNull
    LocalDateTime settledAfter;
    @NotNull
    LocalDateTime settledBefore;


    public SearchSettlementsDto toDto(PageInfo pageInfo) {
        return SearchSettlementsDto.builder()
                .settledAfter(settledAfter)
                .settledBefore(settledBefore)
                .pageRequest(pageInfo.toPageRequest())
                .build();
    }
}
