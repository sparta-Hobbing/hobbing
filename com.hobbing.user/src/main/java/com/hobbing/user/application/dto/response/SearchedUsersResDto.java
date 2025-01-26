package com.hobbing.user.application.dto.response;

import com.hobbing.common.domain.model.UserRole;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SearchedUsersResDto {

    UUID id;
    String nickname;
    String name;
    String email;
    UserRole role;
    String profile;
    String phoneNumber;

    public static SearchedUsersResDto from(
            UserDto user) {
        return SearchedUsersResDto.builder()
                .id(UUID.fromString(user.getId()))
                .nickname(user.getNickname())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .profile(user.getProfile())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

}