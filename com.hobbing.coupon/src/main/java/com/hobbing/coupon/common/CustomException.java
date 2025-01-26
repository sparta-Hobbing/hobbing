package com.hobbing.coupon.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, Integer code, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public CustomException(CommonErrorCode commonErrorCode) {
        this.code = commonErrorCode.getCode();
        this.message = commonErrorCode.getMessage();
        this.httpStatus = commonErrorCode.getHttpStatus();
    }
}
