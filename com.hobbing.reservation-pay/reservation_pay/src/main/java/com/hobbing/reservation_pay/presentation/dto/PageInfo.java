package com.hobbing.reservation_pay.presentation.dto;


import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class PageInfo {

    private int pageSize;
    private int pageNumber;

    public PageInfo(int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc("createdAt")));
    }

}
