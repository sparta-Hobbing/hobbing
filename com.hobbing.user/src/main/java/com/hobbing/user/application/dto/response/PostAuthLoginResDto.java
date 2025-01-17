package com.hobbing.user.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class PostAuthLoginResDto {

    private String accessToken;

    // 동시성 문제를 어떻게 처리하지???
    public static PostAuthLoginResDto of(String accessToken) {
        return PostAuthLoginResDto.builder().accessToken(accessToken).build();
    }

}
