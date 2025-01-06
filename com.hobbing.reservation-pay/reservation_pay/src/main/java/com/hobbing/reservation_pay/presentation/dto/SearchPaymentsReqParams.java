package com.hobbing.reservation_pay.presentation.dto;


import com.hobbing.reservation_pay.application.dto.SearchPaymentsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//@Value
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public SearchPaymentsReqParams(String payedAfter,
                                   String payedBefore) {

        this.payedAfter = LocalDateTime.parse(payedAfter, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.payedBefore = LocalDateTime.parse(payedBefore, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
