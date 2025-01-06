package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.application.dto.SearchPaymentsDto;
import lombok.Value;

import java.time.LocalDateTime;


@Value
public class SearchPaymentsReqParams {
    LocalDateTime payedAfter;
    LocalDateTime payedBefore;


    public SearchPaymentsDto toDto(PageInfo pageInfoParams) {

        return SearchPaymentsDto.builder()
                .payedAfter(payedAfter)
                .payedBefore(payedBefore)
                .pageRequest(pageInfoParams.toPageRequest())
                .build();
    }
}
