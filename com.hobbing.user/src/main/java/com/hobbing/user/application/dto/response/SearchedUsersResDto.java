package com.hobbing.user.application.dto.response;

import com.hobbing.user.domain.model.User;
import com.hobbing.user.domain.model.UserRole;
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

    public static SearchedUsersResDto from(User user) {
        return SearchedUsersResDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .profile(user.getProfile())
                .phoneNumber(user.getPhone_number())
                .build();
    }

}
