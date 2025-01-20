package com.hobbing.common.application.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(force = true)
public class CustomException extends RuntimeException {
    protected Integer code;
    protected String message;
    protected HttpStatus httpStatus;

    public CustomException(CommonErrorCode commonErrorCode) {
        this.code = commonErrorCode.getCode();
        this.message = commonErrorCode.getMessage();
        this.httpStatus = commonErrorCode.getHttpStatus();
    }

}