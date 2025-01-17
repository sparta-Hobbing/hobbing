package com.hobbing.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hobbing.user.domain.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostAuthSignupReqDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Email(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @JsonProperty(value = "user_role")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "권한을 입력해주세요.")
    private UserRole userRole;

    private String profile;

    @JsonProperty(value = "phone_number")
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phoneNumber;
}
