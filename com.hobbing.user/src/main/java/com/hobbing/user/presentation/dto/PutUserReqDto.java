package com.hobbing.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PutUserReqDto {

    @Email(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String profile;

    @JsonProperty(value = "phone_number")
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

}
