package com.hobbing.coupon.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageResponse<T> {

    // Getters and Setters
    private List<T> content;     // 현재 페이지에 해당하는 데이터 목록
    private int pageNumber;      // 현재 페이지 번호
    private int pageSize;        // 페이지 크기 (한 페이지의 항목 수)
    private long totalElements;  // 전체 항목 수
    private int totalPages;      // 전체 페이지 수

    // 생성자
    public PageResponse(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize); // 전체 페이지 수 계산
    }

}
