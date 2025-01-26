package com.hobbing.user.presentation.controller;

import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.common.domain.model.UserRole;
import com.hobbing.user.application.dto.response.SearchedUsersResDto;
import com.hobbing.user.application.dto.response.VerifyResponse;
import com.hobbing.user.application.service.UserService;
import com.hobbing.user.presentation.dto.PageInfo;
import com.hobbing.user.presentation.dto.PutUserReqDto;
import com.hobbing.user.presentation.dto.PutUserRoleDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/verify")
    ApiResponse<VerifyResponse> verify(
            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        VerifyResponse verify = userService.verify(userId, userRole, secretKey);

        return ApiResponse.ofSuccess(HttpStatus.OK, "인증되었습니다.", verify);
    }

    @PutMapping("/{user_id}")
    ApiResponse<Void> updateUser(
            @PathVariable("user_id") @Nullable String id,
            @RequestBody PutUserReqDto dto,

            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        //application dto를 만들어서 userId, putUserReqDto, userId,userRole, secretKey를 같이 넘기는 방법으로 변경예정
        userService.updateUser(id, dto);
        return ApiResponse.ofSuccess(HttpStatus.OK, "회원정보가 수정되었습니다.", null);

    }

    @PutMapping("/{user_id}/role")
    ApiResponse<Void> updateUserRole(
            @PathVariable("user_id") @Nullable String id,
            @RequestBody PutUserRoleDto dto,

            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        //application dto를 만들어서 userId, putUserReqDto, userId,userRole, secretKey를 같이 넘기는 방법으로 변경예정
        userService.updateUserRole(id, dto);
        return ApiResponse.ofSuccess(HttpStatus.OK, "회원정보가 수정되었습니다.", null);
    }

    @GetMapping
    ApiResponse<PagedModel<SearchedUsersResDto>> searchUsers(
            @ModelAttribute @Valid PageInfo pageInfo,
            @RequestParam(name = "start_date", required = false) LocalDateTime startDate,
            @RequestParam(name = "end_date", required = false) LocalDateTime endDate,

            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        Page<SearchedUsersResDto> users = userService.searchUsers(startDate, endDate, pageInfo).map(SearchedUsersResDto::from);

        PagedModel<SearchedUsersResDto> pagedModel = new PagedModel<>(users);

        return ApiResponse.ofSuccess(HttpStatus.OK, "회원정보들을 검색했습니다.", pagedModel);
    }

    @GetMapping("/{user_id}")
    ApiResponse<SearchedUsersResDto> searchUser(
            @PathVariable("user_id") @Nullable String id,

            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        return ApiResponse.ofSuccess(HttpStatus.OK, "회원정보를 검색했습니다.", SearchedUsersResDto.from(userService.searchUser(id)));
    }

    @DeleteMapping("/{user_id}")
    ApiResponse<Void> deleteUser(
            @PathVariable("user_id") @Nullable String id,

            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        userService.deleteUser(id);
        return ApiResponse.ofSuccess(HttpStatus.OK, "회원을 삭제했습니다.", null);
    }

}