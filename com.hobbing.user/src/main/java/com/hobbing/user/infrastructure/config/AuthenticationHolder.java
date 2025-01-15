package com.hobbing.user.infrastructure.config;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthenticationHolder {
    Optional<Authentication> getAuthentication();
    void setAuthentication(Authentication authentication);
}
