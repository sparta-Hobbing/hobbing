package com.hobbing.gateway.infrastructure.client;

import com.hobbing.gateway.application.dto.ApiResponse;
import com.hobbing.gateway.dto.VerifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.hobbing.gateway.infrastructure.util.CustomHeader.*;

@Slf4j
@Service
public class AuthClient {
    private String authHost = "http://cae6d7a5f359";

    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<ResponseEntity<ApiResponse<VerifyResponse>>> validateUserExists(String userId, String userRole, String internalKey, URI uri) {
        return webClient.get()
//                .uri("http://"+ uri.getHost() + ":" + "19020/users/verify")
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(uri.getHost())
                        .port("19020")
                        .path("/users/verify")
                        .build())
                .header(KEY_USER_ID, userId)
                .header(KEY_USER_ROLE, userRole)
                .header(KEY_INTERNAL_KEY, internalKey)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<VerifyResponse>>(){});
    }
}
