package com.hobbing.reservation_pay.presentation.dto;


import jakarta.validation.constraints.Min;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Value
public class PageInfo {

    @Min(3)
    int pageSize;
    @Min(0)
    int pageNumber;


    public PageRequest toPageRequest() {
        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc("createdAt")));
    }

}
