package com.hobbing.user.application.exception;

import com.hobbing.common.application.exception.CustomException;

public class UserException extends CustomException {
    public UserException(UserErrorCode userErrorCode) {
        super.code = userErrorCode.getCode();
        super.message = userErrorCode.getMessage();
        super.httpStatus = userErrorCode.getHttpStatus();
    }
}
