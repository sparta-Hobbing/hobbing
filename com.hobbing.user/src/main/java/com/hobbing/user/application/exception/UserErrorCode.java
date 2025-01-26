package com.hobbing.user.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {
    DUPLICATED_USER_ERROR(HttpStatus.BAD_REQUEST, 1001, "중복된 회원이 존재합니다."),
    NOT_EXISTED_USER_ERROR(HttpStatus.BAD_REQUEST, 1002, "아이디가 존재하지 않습니다."),
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST, 1003, "비밀번호가 일치하지 않습니다."),

    ;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}