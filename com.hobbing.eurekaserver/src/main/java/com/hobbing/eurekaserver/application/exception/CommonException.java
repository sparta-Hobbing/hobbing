package com.hobbing.eurekaserver.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    public CommonException(HttpStatus httpStatus, Integer code, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
