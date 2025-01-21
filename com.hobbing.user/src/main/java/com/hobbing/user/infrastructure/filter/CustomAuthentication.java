package com.hobbing.user.infrastructure.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.UUID;

public class CustomAuthentication extends AbstractAuthenticationToken {

    private final UUID userId;
    private final String userRole;

    public CustomAuthentication(String userId, String userRole) {
        super(Collections.emptyList());
        this.userId = UUID.fromString(userId);;
        this.userRole = userRole;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    public String getUserRole(){
        return userRole;
    }
}