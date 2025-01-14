package com.hobbing.user.presentation.controller;

import com.hobbing.user.application.dto.response.ApiResponse;
import com.hobbing.user.application.dto.response.PostAuthLoginResDto;
import com.hobbing.user.application.service.AuthService;
import com.hobbing.user.presentation.dto.PostAuthLoginReqDto;
import com.hobbing.user.presentation.dto.PostAuthSignupReqDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> signup(@RequestBody @Valid PostAuthSignupReqDto dto){
        log.info("signup");
        authService.createUser(dto);

        return new ResponseEntity<>(ApiResponse.ofSuccess(HttpStatus.CREATED, "회원가입했습니다.",
                null),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<PostAuthLoginResDto>> login(@RequestBody @Valid PostAuthLoginReqDto dto){
        return ResponseEntity.ok(
                ApiResponse.ofSuccess(HttpStatus.OK, "로그인했습니다.",
                authService.createAccessToken(dto))
        );
    }

}
