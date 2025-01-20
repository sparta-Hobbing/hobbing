package com.hobbing.user.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(CommonErrorCode commonErrorCode) {
        this.code = commonErrorCode.getCode();
        this.message = commonErrorCode.getMessage();
        this.httpStatus = commonErrorCode.getHttpStatus();
    }

    public CustomException(UserErrorCode userErrorCode) {
        this.code = userErrorCode.getCode();
        this.message = userErrorCode.getMessage();
        this.httpStatus = userErrorCode.getHttpStatus();
    }

}
