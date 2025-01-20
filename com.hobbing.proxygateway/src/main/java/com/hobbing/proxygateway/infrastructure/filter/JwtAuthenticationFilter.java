package com.hobbing.proxygateway.infrastructure.filter;

import com.hobbing.proxygateway.infrastructure.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.hobbing.proxygateway.infrastructure.util.CustomHeader.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    private final JwtUtil jwtUtil;

    @Value("${service.internal.internal-key}")
    private String INTERNAL_KEY_VALUE;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(KEY_ACCESS_TOKEN);
        String uri = exchange.getRequest().getURI().getPath();
        log.info(uri);
        if (uri.startsWith("/auths")) {
            log.info("Pass the JWT Token Validate, URI: {}", uri);
            return chain.filter(exchange);
        }

        if (token != null && !jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            log.error("JWT Token Validate Fail, Token: {}", token);
            return exchange.getResponse().setComplete();
        }

        String userId = jwtUtil.getUserIdFromToken(token);
        String userRole = jwtUtil.getUserRoleFromToken(token);

//        exchange.getRequest().getHeaders().remove(KEY_ACCESS_TOKEN);

        exchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header(KEY_USER_ID, userId)
                        .header(KEY_USER_ROLE, userRole)
                        .header(KEY_INTERNAL_KEY, INTERNAL_KEY_VALUE)
                        .build()
                )
                .build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}