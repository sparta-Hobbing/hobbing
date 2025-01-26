package com.hobbing.user.presentation.controller;

import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.user.application.dto.response.PostAuthLoginResDto;
import com.hobbing.user.application.service.AuthService;
import com.hobbing.user.presentation.dto.PostAuthLoginReqDto;
import com.hobbing.user.presentation.dto.PostAuthSignupReqDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/health")
    public String signup() {
        return "Test 확인";
    }

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@RequestBody @Valid PostAuthSignupReqDto dto) {
        authService.createUser(dto);

        return ApiResponse.ofSuccess(HttpStatus.CREATED, "회원가입했습니다.", null);
    }

    @PostMapping("/login")
    public ApiResponse<PostAuthLoginResDto> login(@RequestBody @Valid PostAuthLoginReqDto dto, HttpServletRequest request){
        log.info("url : " + request.getRequestURL());
        return ApiResponse.ofSuccess(HttpStatus.OK, "로그인했습니다.", authService.createAccessToken(dto));
    }

}