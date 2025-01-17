package com.hobbing.user.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CommonException extends CustomException {
    public CommonException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
