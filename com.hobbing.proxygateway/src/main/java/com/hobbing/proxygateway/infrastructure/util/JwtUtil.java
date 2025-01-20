package com.hobbing.proxygateway.infrastructure.util;


import com.hobbing.proxygateway.domain.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static com.hobbing.proxygateway.infrastructure.util.CustomHeader.*;

@Slf4j
@Component
public class JwtUtil {
    private final Long ACCESS_TOKEN_EXPIRATION_TIME;
    private final SecretKey SECRET_KEY;

    public JwtUtil(
            @Value("${service.jwt.secret-key}") String secretKey,
            @Value("${service.jwt.access-expiration}") Long accessExpiration
    ) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.ACCESS_TOKEN_EXPIRATION_TIME = accessExpiration;
    }

    private Claims getClaimValueFromToken(String token) {
        token = token.substring(VALUE_BEARER_PREFIX.length());
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        return getClaimValueFromToken(token).get(KEY_USER_ID, String.class);
    }

    public String getUserRoleFromToken(String token) {
        return getClaimValueFromToken(token).get(KEY_USER_ROLE, String.class);
    }

    public LocalDateTime getIssuedAtFromToken(String token) {
        Claims claims = getClaimValueFromToken(token);
        Date issuedAt = claims.getIssuedAt();

        return issuedAt.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public boolean validateToken(String token) {
        try {
            String brokenToken = token.substring(VALUE_BEARER_PREFIX.length());

            Claims payload = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(brokenToken)
                    .getBody();
            log.debug("JWT claims string: {}", payload);

            String issuer = payload.getIssuer();
            if (issuer == null || !issuer.equals(KEY_ISSUER)) {
                log.error("Invalid issuer");
                return false;
            }
            log.debug("Validated token issuer");

            String userId = payload.get(KEY_USER_ID, String.class);
            UUID userIdOrigin = userId != null ? UUID.fromString(userId) : null;
            if (userIdOrigin == null) {
                log.error("Invalid userId");
                return false;
            }
            log.debug("Validated token userId");

            String userRole = payload.get(KEY_USER_ROLE, String.class);
            UserRole userRoleOrigin = userRole != null ? UserRole.valueOf(userRole) : null;
            if (userRoleOrigin == null){
                log.error("Invalid userRole");
                return false;
            }
            log.debug("Validated token userRole");

            Date expiration = payload.getExpiration();
            if (expiration == null || expiration.before(new Date())) {
                log.error("Invalid expiration");
                return false;
            }
            log.debug("Validated token expiration");

            return true;
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException, Token has expired: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("MalformedJwtException, Malformed token: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Exception, Invalid token: {}", e.getMessage());
        }

        return false;
    }

    public String generateAccessToken(Long userId, UserRole userRole) {
        return VALUE_BEARER_PREFIX + Jwts.builder()
                .claim(KEY_USER_ID, String.valueOf(userId))
                .claim(KEY_USER_ROLE, String.valueOf(userRole))
                .setIssuer(KEY_ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

}