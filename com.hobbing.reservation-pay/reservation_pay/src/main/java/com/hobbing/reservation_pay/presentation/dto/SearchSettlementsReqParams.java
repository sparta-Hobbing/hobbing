package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.application.dto.SearchSettlementsDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Value
@Builder
public class SearchSettlementsReqParams {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime settledAfter;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime settledBefore;


    public SearchSettlementsDto toDto(PageInfo pageInfo) {
        return SearchSettlementsDto.builder()
                .settledAfter(settledAfter)
                .settledBefore(settledBefore)
                .pageRequest(pageInfo.toPageRequest())
                .build();
    }
}
