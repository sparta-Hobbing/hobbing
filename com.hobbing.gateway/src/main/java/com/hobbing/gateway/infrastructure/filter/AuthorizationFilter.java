//package com.hobbing.gateway.infrastructure.filter;
//
//import com.hobbing.common.application.dto.ApiResponse;
//import com.hobbing.common.domain.model.UserRole;
//import com.hobbing.gateway.dto.VerifyResponse;
//import com.hobbing.gateway.infrastructure.client.AuthClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.PathContainer;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.util.pattern.PathPattern;
//import org.springframework.web.util.pattern.PathPatternParser;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//
//import static com.hobbing.common.infrastructure.util.CustomHeader.*;
//
//@Slf4j
//@Component
//public abstract class AuthorizationFilter{
//
//    @Value("${service.internal.interanl-key}")
//    private String INTERNAL_KEY;
//
//    private final AuthClient authClient;
//
//    public AuthorizationFilter(Class<T> configClass, AuthClient authClient) {
//        super(configClass);
//        this.authClient = authClient;
//    }
//
//    @Override
//    public GatewayFilter apply(T config) {
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            String path = request.getURI().getPath();
//            HttpMethod method = request.getMethod();
//            String userId = request.getHeaders().getFirst(KEY_USER_ID);
//            String userRole = request.getHeaders().getFirst(KEY_USER_ROLE);
//            String internalKey = request.getHeaders().getFirst(KEY_INTERNAL_KEY);
//
//            if (userId == null || userRole == null || internalKey == null) {
//                return errorResponse(exchange, "Missing required headers.");
//            }
//
//            if (!internalKey.equals(INTERNAL_KEY)) {
//                return errorResponse(exchange, "Invalid internal key.");
//            }
//
//            if (!validatePath(path)) {
//                return errorResponse(exchange, "Invalid path.");
//            }
//
//            ApiResponse<VerifyResponse> body = authClient.validateUserExists(userId, userRole, internalKey)
//                    .block()
//                    .getBody();
//
//            VerifyResponse data = body.data();
//            if (data == null || !data.isVerified()) {
//                return errorResponse(exchange, "Permission denied.");
//            }
//
//            if (checkPathPermissions(path, method, userRole)) {
//                return chain.filter(exchange);
//            } else {
//                return errorResponse(exchange, "Permission denied.");
//            }
//        });
//    }
//
//    protected abstract boolean validatePath(String path);
//
//    protected abstract boolean checkPathPermissions(String path, HttpMethod method, String userRole);
//
//    protected boolean matchesPathPattern(PathPatternParser parser, PathContainer pathContainer, String pattern) {
//        PathPattern pathPattern = parser.parse(pattern);
//        return pathPattern.matches(pathContainer);
//    }
//
//    protected boolean checkRole(UserRole[] userRoles, String role) {
//        return Arrays.stream(userRoles).anyMatch(userRole -> userRole.name().equals(role));
//    }
//
//    private Mono<Void> errorResponse(ServerWebExchange exchange, String message) {
//        log.error(message);
//        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//        return exchange.getResponse().setComplete();
//    }
//
//    public static class Config {}
//}
