package com.hobbing.user.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class PostAuthLoginResDto {

    private String accessToken;

    public static PostAuthLoginResDto of(String accessToken) {
        return PostAuthLoginResDto.builder().accessToken(accessToken).build();
    }

}