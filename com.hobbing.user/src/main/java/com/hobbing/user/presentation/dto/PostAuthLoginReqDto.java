package com.hobbing.user.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostAuthLoginReqDto {

    @Valid
    @NotBlank(message = "아이디를 입력하세요.")
    private String nickname;

    @Valid
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

}