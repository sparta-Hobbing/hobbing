package com.hobbing.eurekaserver.application.exception;

import com.hobbing.eurekaserver.application.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice(annotations = RestController.class) // 개별 컨트롤러마다 추가하는 방향으로 가야 함
@RestControllerAdvice
public class CommonExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> commonExceptionHandle(CustomException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ApiResponse.ofError(e.getCode(), e.getMessage()), e.getHttpStatus());
    }

}
