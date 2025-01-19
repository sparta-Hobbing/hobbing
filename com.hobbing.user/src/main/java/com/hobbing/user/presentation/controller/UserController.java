package com.hobbing.user.presentation.controller;

<<<<<<< HEAD
import com.hobbing.user.application.dto.SearchUsersReqDto;
import com.hobbing.user.application.dto.response.ApiResponse;
import com.hobbing.user.application.dto.response.SearchedUsersResDto;
import com.hobbing.user.application.dto.response.VerifyResponse;
import com.hobbing.user.application.service.UserService;
import com.hobbing.user.domain.model.User;
=======
import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.user.application.dto.response.SearchedUsersResDto;
import com.hobbing.user.application.dto.response.VerifyResponse;
import com.hobbing.user.application.service.UserService;
>>>>>>> dev
import com.hobbing.user.domain.model.UserRole;
import com.hobbing.user.infrastructure.PageInfo;
import com.hobbing.user.presentation.dto.PutUserReqDto;
import com.hobbing.user.presentation.dto.PutUserRoleDto;
import jakarta.annotation.Nullable;
<<<<<<< HEAD
import jakarta.ws.rs.PUT;
=======
>>>>>>> dev
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
=======
>>>>>>> dev
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
<<<<<<< HEAD
@Controller
=======
@RestController
>>>>>>> dev
public class UserController {
    private final UserService userService;

    @GetMapping("/verify")
<<<<<<< HEAD
    ResponseEntity<ApiResponse<VerifyResponse>> verify(
=======
    ApiResponse<VerifyResponse> verify(
>>>>>>> dev
            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        VerifyResponse verify = userService.verify(userId, userRole, secretKey);

<<<<<<< HEAD
        return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK, "인증되었습니다.", verify));
    }

    @PutMapping("/{user_id}")
    ResponseEntity<ApiResponse<Void>> updateUser(
            @PathVariable("user_id") @Nullable String id,
            @RequestBody PutUserReqDto dto
//            ,

//            @RequestHeader(name="user_id") @Nullable String userId,
//            @RequestHeader(name="user_role") @Nullable UserRole userRole,
//            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        //application dto를 만들어서 userId, putUserReqDto, userId,userRole, secretKey를 같이 넘기는 방법으로 변경예정
        userService.updateUser(id, dto);
        return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK, "회원정보가 수정되었습니다.", null));
=======
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
>>>>>>> dev

    }

    @PutMapping("/{user_id}/role")
<<<<<<< HEAD
    ResponseEntity<ApiResponse<Void>> updateUserRole(
            @PathVariable("user_id") @Nullable String id,
            @RequestBody PutUserRoleDto dto
//            ,

//            @RequestHeader(name="user_id") @Nullable String userId,
//            @RequestHeader(name="user_role") @Nullable UserRole userRole,
//            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        //application dto를 만들어서 userId, putUserReqDto, userId,userRole, secretKey를 같이 넘기는 방법으로 변경예정
        userService.updateUserRole(id, dto);
        return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK, "회원정보가 수정되었습니다.", null));
    }

    @GetMapping
    ResponseEntity<ApiResponse<PagedModel<SearchedUsersResDto>>> searchUsers(
            @ModelAttribute PageInfo pageInfo
//            ,
//            @ModelAttribute SearchUsersReqDto dto,

//            @RequestHeader(name="user_id") @Nullable String userId,
//            @RequestHeader(name="user_role") @Nullable UserRole userRole,
//            @RequestHeader(name="secret_key") @Nullable String secretKey
=======
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
            @ModelAttribute PageInfo pageInfo,
//            @ModelAttribute SearchUsersReqDto dto,

            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
>>>>>>> dev
    ){
        Page<SearchedUsersResDto> users = userService.searchUsers(pageInfo).map(SearchedUsersResDto::from);

        PagedModel<SearchedUsersResDto> pagedModel = new PagedModel<>(users);

<<<<<<< HEAD
        return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK, "회원정보들을 검색했습니다.",
                pagedModel));
    }

    @GetMapping("/{user_id}")
    ResponseEntity<ApiResponse<?>> searchUser(
            @PathVariable("user_id") @Nullable String id
//            ,

//            @RequestHeader(name="user_id") @Nullable String userId,
//            @RequestHeader(name="user_role") @Nullable UserRole userRole,
//            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK, "회원정보를 검색했습니다.",
                userService.searchUser(id)));
=======
        return ApiResponse.ofSuccess(HttpStatus.OK, "회원정보들을 검색했습니다.", pagedModel);
    }

    @GetMapping("/{user_id}")
    ApiResponse<SearchedUsersResDto> searchUser(
            @PathVariable("user_id") @Nullable String id,

            @RequestHeader(name="user_id") @Nullable String userId,
            @RequestHeader(name="user_role") @Nullable UserRole userRole,
            @RequestHeader(name="secret_key") @Nullable String secretKey
    ){
        return ApiResponse.ofSuccess(HttpStatus.OK, "회원정보를 검색했습니다.", userService.searchUser(id));
>>>>>>> dev
    }

}
