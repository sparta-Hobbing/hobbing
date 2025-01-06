package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.application.dto.SearchPaymentsDto;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Value
public class SearchPaymentsReqParams {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime payedAfter;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime payedBefore;


    public SearchPaymentsDto toDto(PageInfo pageInfoParams) {

        return SearchPaymentsDto.builder()
                .payedAfter(payedAfter)
                .payedBefore(payedBefore)
                .pageRequest(pageInfoParams.toPageRequest())
                .build();
    }
}
