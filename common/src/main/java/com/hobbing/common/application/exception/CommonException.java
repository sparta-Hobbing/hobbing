package com.hobbing.common.application.exception;

public class CommonException extends CustomException {
    public CommonException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}