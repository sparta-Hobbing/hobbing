package com.hobbing.user.application.dto.response;

import com.hobbing.common.domain.model.UserRole;
import com.hobbing.user.domain.model.User;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private String id;
    private String nickname;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private String profile;
    private String phoneNumber;
    private boolean deleted;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId().toString())
                .nickname(user.getNickname())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .profile(user.getProfile())
                .phoneNumber(user.getPhone_number())
                .deleted(user.isDeleted())
                .build();
    }

}