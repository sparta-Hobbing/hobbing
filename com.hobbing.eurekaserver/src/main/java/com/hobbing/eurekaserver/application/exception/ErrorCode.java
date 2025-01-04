package com.hobbing.eurekaserver.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    COMMON_SAMPLE_ERROR(HttpStatus.BAD_REQUEST, 1101, "샘플에러메시지입니다."),

    ;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
