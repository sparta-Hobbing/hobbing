package com.hobbing.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hobbing.common.domain.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PutUserRoleDto {

    @JsonProperty(value = "user_role")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "권한을 입력해주세요.")
    private UserRole userRole;

}
