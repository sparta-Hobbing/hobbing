package com.hobbing.eurekaserver.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


// 개별 작성
@Getter
public enum CommonErrorCode {
    COMMON_SAMPLE_ERROR(HttpStatus.BAD_REQUEST, 1001, "샘플에러메시지입니다."),

    ;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
