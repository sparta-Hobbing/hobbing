package com.hobbing.reservation_pay.application.dto;


import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;


@Value
@Builder
public class SearchPaymentsDto {

    LocalDateTime payedAfter;
    LocalDateTime payedBefore;
    PageRequest pageRequest;
}


