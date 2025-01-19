package com.hobbing.gateway.infrastructure.client;

<<<<<<< HEAD
import com.hobbing.gateway.domain.CustomHeader;
import com.hobbing.gateway.domain.UserRole;
import com.hobbing.gateway.dto.ApiResponse;
import com.hobbing.gateway.dto.VerifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AuthClient {
//    @Value("${auth.host}")
=======
import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.gateway.dto.VerifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.hobbing.common.infrastructure.util.CustomHeader.*;

@Slf4j
@Service
public class AuthClient {
>>>>>>> dev
    private String authHost = "http://localhost:19020";

    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<ResponseEntity<ApiResponse<VerifyResponse>>> validateUserExists(String userId, String userRole, String internalKey) {
        return webClient.get()
                .uri(authHost + "/users/verify")
<<<<<<< HEAD
                .header(CustomHeader.KEY_USER_ID, userId)
                .header(CustomHeader.KEY_USER_ROLE, userRole)
                .header(CustomHeader.KEY_INTERNAL_KEY, internalKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));})
=======
                .header(KEY_USER_ID, userId)
                .header(KEY_USER_ROLE, userRole)
                .header(KEY_INTERNAL_KEY, internalKey)
                .retrieve()
>>>>>>> dev
                .toEntity(new ParameterizedTypeReference<ApiResponse<VerifyResponse>>(){});
    }
}
