package com.hobbing.eurekaserver.application.dto;

import com.hobbing.eurekaserver.application.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        Integer code,
        String message,
        T data
) {

    public static ApiResponse<Void> ofError(Integer code, String description){
        return new ApiResponse<>(code, description, null);
    }

    public static <T> ApiResponse<T> ofSuccess(HttpStatus status, String description, T data){
        return new ApiResponse<>(status.value(), description, data);
    }
}
