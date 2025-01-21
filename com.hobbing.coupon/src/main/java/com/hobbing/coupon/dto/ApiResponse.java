package com.hobbing.coupon.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {

    // Getters and Setters
    private String status;  // SUCCESS 또는 ERROR
    private String message; // 메시지
    private T data;         // 응답 데이터

    // 생성자
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 성공 응답을 생성하는 메서드
    public static <T> ApiResponse<T> ofSuccess(String message, T data) {
        return new ApiResponse<>("SUCCESS", message, data);
    }

}
