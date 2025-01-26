package com.hobbing.user.application.service;

import com.hobbing.common.domain.model.UserRole;
import com.hobbing.user.application.dto.response.UserDto;
import com.hobbing.user.application.dto.response.VerifyResponse;
import com.hobbing.user.application.exception.UserErrorCode;
import com.hobbing.user.application.exception.UserException;
import com.hobbing.user.domain.model.User;
import com.hobbing.user.domain.repository.UserRepository;
import com.hobbing.user.presentation.dto.PageInfo;
import com.hobbing.user.presentation.dto.PutUserReqDto;
import com.hobbing.user.presentation.dto.PutUserRoleDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, UserDto> userRedisTemplate;
    private final ValueOperations<String, UserDto> userListOps;

    @Value("${service.internal.internal-key}")
    private String secretKey;

    public UserService(UserRepository userRepository, RedisTemplate<String, UserDto> userRedisTemplate) {
        this.userRepository = userRepository;
        this.userRedisTemplate = userRedisTemplate;
        this.userListOps = this.userRedisTemplate.opsForValue();
    }


    public VerifyResponse verify(String userId, UserRole userRole, String secretKey) {
        UserDto userDto = userListOps.get("userCache::"+userId);
        User user;
        if(userDto == null || userDto.isDeleted()){
            user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(userId))
                    .orElse(null);
            userListOps.set("userCache::"+user.getId(), UserDto.fromEntity(user));
        }else{
            user = User.getUserRedis(userDto);
        }

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
    @CachePut(cacheNames = "userCache", key = "#id")
    public UserDto updateUser(String id, PutUserReqDto dto) {
        User user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));
        user.modifyUser(dto);
        return UserDto.fromEntity(user);
    }

    @CachePut(cacheNames = "userCache", key = "#id")
    @Transactional
    public UserDto updateUserRole(String id, PutUserRoleDto dto) {
        User user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));
        user.modifyUserRole(dto.getUserRole());
        return UserDto.fromEntity(user);
    }

    @Cacheable(cacheNames = "userAllCache", key = "methodName")
    public Page<UserDto> searchUsers(LocalDateTime startDate, LocalDateTime endDate, PageInfo pageInfo) {
        LocalDateTime changedStartDate = startDate;
        LocalDateTime changedEndDate = endDate;
        if(startDate == null){
            changedStartDate = LocalDateTime.now().minusMonths(1);
        }

        if(endDate == null){
            changedEndDate = LocalDateTime.now();
        }

        return userRepository.findUsers(changedStartDate, changedEndDate, pageInfo).map(UserDto::fromEntity);
    }

    @Cacheable(cacheNames = "userCache", key = "#id")
    public UserDto searchUser(String id) {
        User userDetails = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));

        return UserDto.fromEntity(userDetails);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "userCache", key = "#id"),
            @CacheEvict(cacheNames = "userAllCache", allEntries = true)
    })
    public void deleteUser(String id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));
        user.delete();
    }
}