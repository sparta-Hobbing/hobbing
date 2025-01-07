package com.hobbing.reservation_pay.presentation.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Value
public class PageInfo {

    @NotNull
    @Min(1)
    Integer pageSize;
    @NotNull
    @Min(0)
    Integer pageNumber;


    public PageRequest toPageRequest() {
        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc("createdAt")));
    }

}
