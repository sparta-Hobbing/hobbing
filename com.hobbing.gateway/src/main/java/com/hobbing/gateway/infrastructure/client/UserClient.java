package com.hobbing.gateway.infrastructure.client;

import com.hobbing.gateway.dto.ApiResponse;
import com.hobbing.gateway.domain.UserRole;
import com.hobbing.gateway.dto.VerifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user")
public interface UserClient {

//    @GetMapping(value = "/users/verify", path = "http:///127.0.0.1:19020/users/verify")
    @GetMapping(value = "/users/verify")
    ResponseEntity<ApiResponse<VerifyResponse>> verify(
            @RequestHeader(name="user_id") String userId,
                                                       @RequestHeader(name="user_role") UserRole userRole,
                                                       @RequestHeader(name="secret_key") String secretKey
            );
}
