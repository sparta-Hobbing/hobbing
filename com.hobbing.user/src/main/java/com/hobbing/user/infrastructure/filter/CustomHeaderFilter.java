package com.hobbing.user.infrastructure.filter;

import com.hobbing.user.domain.model.CustomHeader;
import com.hobbing.user.domain.model.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomHeaderFilter extends OncePerRequestFilter {

    @Value("${service.internal.internal-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().startsWith("/auths")) {
            filterChain.doFilter(request, response);
        }
        String userId =request.getHeader(CustomHeader.KEY_USER_ID);
        String userRole = request.getHeader(CustomHeader.KEY_USER_ROLE);
        String secretKey = request.getHeader(CustomHeader.KEY_INTERNAL_KEY);


        if(isValid(userId, userRole, secretKey)){
            CustomAuthentication authentication = new CustomAuthentication(userId, userRole);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValid(String userId, String userRole, String secretKey) {
        return userId != null && checkRole(userRole) && secretKey.equals(this.secretKey);
    }

    private boolean checkRole(String userRole) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equals(userRole)) {
                return true;
            }
        }
        return false;
    }
}
