package com.hobbing.common.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode {

    ,
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