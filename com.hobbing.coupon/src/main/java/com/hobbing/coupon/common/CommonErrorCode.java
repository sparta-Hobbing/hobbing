package com.hobbing.coupon.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode {

    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, 5000, "쿠폰 정보를 찾을 수 없습니다."),
    COUPON_EXPIRED(HttpStatus.BAD_REQUEST, 5001, "이미 만료된 쿠폰입니다."),
    COUPON_ALREADY_USED(HttpStatus.BAD_REQUEST, 5002, "이미 사용된 쿠폰입니다."),
    COUPON_ISSUE_LIMIT_REACHED(HttpStatus.BAD_REQUEST, 5003, "쿠폰 최대 발급 수를 초과했습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, 5004, "쿠폰에 대한 접근 권한이 없습니다."),
    INVALID_COUPON_STATE(HttpStatus.BAD_REQUEST, 5005, "쿠폰 상태가 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}