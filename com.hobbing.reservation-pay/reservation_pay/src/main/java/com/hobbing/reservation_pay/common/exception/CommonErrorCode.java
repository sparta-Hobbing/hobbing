package com.hobbing.reservation_pay.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


// 개별 작성
@Getter
public enum CommonErrorCode {
    COMMON_SAMPLE_ERROR(HttpStatus.BAD_REQUEST, 1001, "샘플에러메시지입니다."),

    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, 3000, "예약정보를 찾을 수 없습니다."),

    RESERVATION_ALREADY_PAYED(HttpStatus.BAD_REQUEST, 3001, "이미 결제된 예약입니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 3002, "결제 정보를 찾을 수 없습니다."),
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
