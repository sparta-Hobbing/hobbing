package com.hobbing.user.application.service;

import com.hobbing.user.application.dto.response.SearchedUsersResDto;
import com.hobbing.user.application.dto.response.VerifyResponse;
import com.hobbing.user.application.exception.UserErrorCode;
import com.hobbing.user.application.exception.UserException;
import com.hobbing.user.domain.model.User;
import com.hobbing.user.domain.model.UserRole;
import com.hobbing.user.domain.repository.UserRepository;
import com.hobbing.user.infrastructure.PageInfo;
import com.hobbing.user.presentation.dto.PutUserReqDto;
import com.hobbing.user.presentation.dto.PutUserRoleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Value("${service.internal.internal-key}")
    private String secretKey;

    public VerifyResponse verify(String userId, UserRole userRole, String secretKey) {
        User user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(userId))
                .orElse(null);

        boolean verified = true;
        if(user == null) {
            return new VerifyResponse(false);
        }
        if(user.getRole() != userRole) {
            verified = false;
        }
        if(!this.secretKey.equals(secretKey)){
            verified = false;
        }
        return new VerifyResponse(verified);
    }

    @Transactional
    public void updateUser(String id, PutUserReqDto dto) {
        User user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));
        user.modifyUser(dto);
    }

    @Transactional
    public void updateUserRole(String id, PutUserRoleDto dto) {
        User user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));
        user.modifyUserRole(dto.getUserRole());
    }

    public Page<User> searchUsers(PageInfo pageInfo) {

        return userRepository.findUsers(pageInfo);
    }

    public SearchedUsersResDto searchUser(String id) {
        User userDetails = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));

        return SearchedUsersResDto.from(userDetails);
    }
}
