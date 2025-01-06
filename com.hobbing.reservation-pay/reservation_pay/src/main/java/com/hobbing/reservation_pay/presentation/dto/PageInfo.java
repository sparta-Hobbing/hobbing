package com.hobbing.reservation_pay.presentation.dto;


import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Value
public class PageInfo {

    int pageSize;
    int pageNumber;


    public PageRequest toPageRequest() {
        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc("createdAt")));
    }

}
