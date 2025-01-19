package com.hobbing.proxygateway.infrastructure.util;

<<<<<<< HEAD
import com.hobbing.proxygateway.domain.CustomHeader;
import com.hobbing.proxygateway.domain.UserRoleEnum;
=======

import com.hobbing.common.domain.model.UserRole;
>>>>>>> dev
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

<<<<<<< HEAD
=======
import static com.hobbing.common.infrastructure.util.CustomHeader.*;

>>>>>>> dev
@Slf4j
@Component
public class JwtUtil {
    private final Long ACCESS_TOKEN_EXPIRATION_TIME;
    private final SecretKey SECRET_KEY;
<<<<<<< HEAD
    private final String USERID = "user_id";
    private final String USERROLE = "user_role";
    private final String ISSUER = "user";
=======
>>>>>>> dev

    public JwtUtil(
            @Value("${service.jwt.secret-key}") String secretKey,
            @Value("${service.jwt.access-expiration}") Long accessExpiration
    ) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.ACCESS_TOKEN_EXPIRATION_TIME = accessExpiration;
    }

    private Claims getClaimValueFromToken(String token) {
<<<<<<< HEAD
        token = token.substring(CustomHeader.VALUE_BEARER_PREFIX.length());
=======
        token = token.substring(VALUE_BEARER_PREFIX.length());
>>>>>>> dev
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
<<<<<<< HEAD
        return getClaimValueFromToken(token).get(USERID, String.class);
    }

    public String getUserRoleFromToken(String token) {
        return getClaimValueFromToken(token).get(USERROLE, String.class);
=======
        return getClaimValueFromToken(token).get(KEY_USER_ID, String.class);
    }

    public String getUserRoleFromToken(String token) {
        return getClaimValueFromToken(token).get(KEY_USER_ROLE, String.class);
>>>>>>> dev
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
<<<<<<< HEAD
            String brokenToken = token.substring(CustomHeader.VALUE_BEARER_PREFIX.length());
=======
            String brokenToken = token.substring(VALUE_BEARER_PREFIX.length());
>>>>>>> dev

            Claims payload = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(brokenToken)
                    .getBody();
            log.debug("JWT claims string: {}", payload);

            String issuer = payload.getIssuer();
<<<<<<< HEAD
            if (issuer == null || !issuer.equals(ISSUER)) {
=======
            if (issuer == null || !issuer.equals(KEY_ISSUER)) {
>>>>>>> dev
                log.error("Invalid issuer");
                return false;
            }
            log.debug("Validated token issuer");

<<<<<<< HEAD
            String userId = payload.get(USERID, String.class);
=======
            String userId = payload.get(KEY_USER_ID, String.class);
>>>>>>> dev
            UUID userIdOrigin = userId != null ? UUID.fromString(userId) : null;
            if (userIdOrigin == null) {
                log.error("Invalid userId");
                return false;
            }
            log.debug("Validated token userId");

<<<<<<< HEAD
            String userRole = payload.get(USERROLE, String.class);
            UserRoleEnum userRoleOrigin = userRole != null ? UserRoleEnum.valueOf(userRole) : null;
=======
            String userRole = payload.get(KEY_USER_ROLE, String.class);
            UserRole userRoleOrigin = userRole != null ? UserRole.valueOf(userRole) : null;
>>>>>>> dev
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

<<<<<<< HEAD
    public String generateAccessToken(Long userId, UserRoleEnum userRole) {
        return CustomHeader.VALUE_BEARER_PREFIX + Jwts.builder()
                .claim(USERID, String.valueOf(userId))
                .claim(USERROLE, String.valueOf(userRole))
                .setIssuer(ISSUER)
=======
    public String generateAccessToken(Long userId, UserRole userRole) {
        return VALUE_BEARER_PREFIX + Jwts.builder()
                .claim(KEY_USER_ID, String.valueOf(userId))
                .claim(KEY_USER_ROLE, String.valueOf(userRole))
                .setIssuer(KEY_ISSUER)
>>>>>>> dev
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

}
