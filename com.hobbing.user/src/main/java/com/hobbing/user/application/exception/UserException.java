package com.hobbing.user.application.exception;

import org.springframework.http.HttpStatus;

public class UserException extends CustomException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
