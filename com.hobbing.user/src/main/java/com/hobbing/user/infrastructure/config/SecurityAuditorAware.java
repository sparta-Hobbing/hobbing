package com.hobbing.user.infrastructure.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

//public class SecurityAuditorAware implements AuditorAware<String> {
public class SecurityAuditorAware implements AuditorAware<UUID> {
    @Override
//    public Optional<String> getCurrentAuditor() {
    public Optional<UUID> getCurrentAuditor() {
        // 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty(); // 인증되지 않은 경우 처리
        }

        // 인증된 사용자 이름 반환
        return Optional.of(UUID.fromString(authentication.getPrincipal().toString()));
    }
}
