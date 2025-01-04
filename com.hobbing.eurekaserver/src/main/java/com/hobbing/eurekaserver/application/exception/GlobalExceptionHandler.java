package com.hobbing.eurekaserver.application.exception;

import com.hobbing.eurekaserver.application.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiResponse<Void>> commonExceptionHandler(CommonException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ApiResponse.ofError(e.getCode(), e.getMessage()), e.getHttpStatus());
    }

}
