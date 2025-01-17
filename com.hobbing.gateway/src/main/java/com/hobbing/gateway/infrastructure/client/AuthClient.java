package com.hobbing.gateway.infrastructure.client;

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
    private String authHost = "http://localhost:19020";

    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<ResponseEntity<ApiResponse<VerifyResponse>>> validateUserExists(String userId, String userRole, String internalKey) {
        return webClient.get()
                .uri(authHost + "/users/verify")
                .header(KEY_USER_ID, userId)
                .header(KEY_USER_ROLE, userRole)
                .header(KEY_INTERNAL_KEY, internalKey)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<VerifyResponse>>(){});
    }
}
