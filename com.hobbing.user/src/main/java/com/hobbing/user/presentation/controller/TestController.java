package com.hobbing.user.presentation.controller;

import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.user.presentation.dto.PostAuthSignupReqDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/")
    public ApiResponse<String> signup() {
        return ApiResponse.ofSuccess(HttpStatus.OK, "회원가입했습니다.", "Test 확인");
    }

}
