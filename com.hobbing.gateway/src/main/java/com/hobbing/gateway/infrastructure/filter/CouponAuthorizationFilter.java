package com.hobbing.gateway.infrastructure.filter;

<<<<<<< HEAD
import com.hobbing.gateway.domain.CustomHeader;
import com.hobbing.gateway.domain.UrlEnum;
import com.hobbing.gateway.domain.UserRole;
import com.hobbing.gateway.dto.ApiResponse;
=======
import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.common.infrastructure.util.CustomHeader;
import com.hobbing.common.domain.model.UserRole;
import com.hobbing.gateway.domain.UrlEnum;
>>>>>>> dev
import com.hobbing.gateway.dto.VerifyResponse;
import com.hobbing.gateway.infrastructure.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Component
public class CouponAuthorizationFilter
        extends AbstractGatewayFilterFactory<CouponAuthorizationFilter.Config> {
    @Value("${service.internal.internal-key}")
    private String INTERNAL_KEY;

    private AuthClient authClient;

    public CouponAuthorizationFilter(AuthClient authClient) {
        super(CouponAuthorizationFilter.Config.class);
        this.authClient = authClient;
    }

    @Override
    public GatewayFilter apply(CouponAuthorizationFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            HttpMethod method = request.getMethod();
            String userId = request.getHeaders().getFirst(CustomHeader.KEY_USER_ID);
            String userRole = request.getHeaders().getFirst(CustomHeader.KEY_USER_ROLE);
            String internalKey = request.getHeaders().getFirst(CustomHeader.KEY_INTERNAL_KEY);

<<<<<<< HEAD
            // 에러 처리 메서드로 공통화
=======
>>>>>>> dev
            if (userId == null || userRole == null || internalKey == null) {
                return errorResponse(exchange, "Missing required headers.");
            }

            if (!internalKey.equals(INTERNAL_KEY)) {
                return errorResponse(exchange, "Invalid internal key.");
            }

            if (!path.startsWith(UrlEnum.PREFIX_USERS.getUrl())) {
                return errorResponse(exchange, "Invalid path prefix.");
            }

<<<<<<< HEAD
            //권한일치 확인로직
=======
>>>>>>> dev
            ApiResponse<VerifyResponse> body = authClient.validateUserExists(userId, userRole, internalKey)
                    .block()
                    .getBody();
            VerifyResponse data  = body.data();
            if(data == null || !data.isVerified()){
                return errorResponse(exchange, "Permission denied.");
            }

<<<<<<< HEAD

=======
>>>>>>> dev
            if (checkPathPermissions(path, method, userRole)) {
                return chain.filter(exchange);
            } else {
                return errorResponse(exchange, "Permission denied.");
            }
        };
    }

    public static class Config {}

    private boolean checkPathPermissions(String path, HttpMethod method, String userRole) {
        PathPatternParser patternParser = new PathPatternParser();
        PathContainer pathContainer = PathContainer.parsePath(path);

        // Paths and roles validation
        if (matchesPathPattern(patternParser, pathContainer, "/coupons")) {
            return (method == HttpMethod.GET || method == HttpMethod.POST)
                    && checkRole(new UserRole[]{UserRole.MASTER, UserRole.MANAGER}, userRole);
        }
        if (matchesPathPattern(patternParser, pathContainer, "/coupons/disable-expired")) {
            return method == HttpMethod.POST && checkRole(new UserRole[]{UserRole.MASTER}, userRole);
        }
        if (matchesPathPattern(patternParser, pathContainer, "/coupon/restore")) {
            return method == HttpMethod.POST && checkRole(new UserRole[]{UserRole.MASTER}, userRole);
        }
        if (matchesPathPattern(patternParser, pathContainer, "/coupons/{couponId}")) {
            return (method == HttpMethod.PUT || method == HttpMethod.DELETE)
                    && checkRole(new UserRole[]{UserRole.MASTER, UserRole.MANAGER}, userRole);
        }
        if (matchesPathPattern(patternParser, pathContainer, "/coupons/{couponId}/issue")) {
            return method == HttpMethod.POST;
        }
        if (matchesPathPattern(patternParser, pathContainer, "/usercoupons/{userCouponId}")) {
            return method == HttpMethod.POST;
        }
        if (matchesPathPattern(patternParser, pathContainer, "/users/me/coupons")) {
            return method == HttpMethod.GET;
        }

        return false;
    }

    private boolean matchesPathPattern(PathPatternParser parser, PathContainer pathContainer, String pattern) {
        PathPattern pathPattern = parser.parse(pattern);
        return pathPattern.matches(pathContainer);
    }

    private boolean checkRole(UserRole[] userRoles, String role) {
        return Arrays.stream(userRoles).anyMatch(userRole -> userRole.name().equals(role));
    }

    // 에러 응답 공통 처리
    private Mono<Void> errorResponse(ServerWebExchange exchange, String message) {
        log.error(message);
        // 예시로 `error`라는 필드를 추가한 에러 응답을 반환
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}
<<<<<<< HEAD
=======

//@Slf4j
//public class CouponAuthorizationFilter extends AuthorizationFilter {
//
//    public CouponAuthorizationFilter(AuthClient authClient) {
//        super(CouponAuthorizationFilter.Config.class, authClient);
//    }
//
//    @Override
//    protected boolean validatePath(String path) {
//        return path.startsWith(UrlEnum.PREFIX_USERS.getUrl());
//    }
//
//    @Override
//    protected boolean checkPathPermissions(String path, HttpMethod method, String userRole) {
//        PathPatternParser patternParser = new PathPatternParser();
//        PathContainer pathContainer = PathContainer.parsePath(path);
//
//        if (matchesPathPattern(patternParser, pathContainer, "/coupons")) {
//            return (method == HttpMethod.GET || method == HttpMethod.POST)
//                    && checkRole(new UserRole[]{UserRole.MASTER, UserRole.MANAGER}, userRole);
//        }
//        if (matchesPathPattern(patternParser, pathContainer, "/coupons/disable-expired")) {
//            return method == HttpMethod.POST && checkRole(new UserRole[]{UserRole.MASTER}, userRole);
//        }
//        if (matchesPathPattern(patternParser, pathContainer, "/coupon/restore")) {
//            return method == HttpMethod.POST && checkRole(new UserRole[]{UserRole.MASTER}, userRole);
//        }
//        if (matchesPathPattern(patternParser, pathContainer, "/coupons/{couponId}")) {
//            return (method == HttpMethod.PUT || method == HttpMethod.DELETE)
//                    && checkRole(new UserRole[]{UserRole.MASTER, UserRole.MANAGER}, userRole);
//        }
//        if (matchesPathPattern(patternParser, pathContainer, "/coupons/{couponId}/issue")) {
//            return method == HttpMethod.POST;
//        }
//        if (matchesPathPattern(patternParser, pathContainer, "/usercoupons/{userCouponId}")) {
//            return method == HttpMethod.POST;
//        }
//        if (matchesPathPattern(patternParser, pathContainer, "/users/me/coupons")) {
//            return method == HttpMethod.GET;
//        }
//
//        return false;
//    }
//
//    private static class Config extends AuthorizationFilter.Config {}
//
//}
>>>>>>> dev
