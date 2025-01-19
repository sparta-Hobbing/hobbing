package com.hobbing.user.application.exception;

import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.common.application.exception.GlobalExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionAdvice extends GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> userExceptionHandle(UserException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ApiResponse.ofError(e.getCode(), e.getMessage()), e.getHttpStatus());
    }

}
