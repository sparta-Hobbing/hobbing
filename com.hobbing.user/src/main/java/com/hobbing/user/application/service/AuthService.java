package com.hobbing.user.application.service;

import com.hobbing.user.application.dto.response.PostAuthLoginResDto;
import com.hobbing.user.application.exception.UserErrorCode;
import com.hobbing.user.application.exception.UserException;
import com.hobbing.user.domain.model.User;
import com.hobbing.user.domain.repository.UserRepository;
import com.hobbing.user.presentation.dto.PostAuthLoginReqDto;
import com.hobbing.user.presentation.dto.PostAuthSignupReqDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${spring.application.name}")
    private String issuer;
    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;
    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Transactional
    public void createUser(PostAuthSignupReqDto dto){
        User user = userRepository.findByNickname(dto.getNickname())
                .orElse(null);

        if(user != null){
            throw new UserException(UserErrorCode.DUPLICATED_USER_ERROR);
        }

        User createdUser = userRepository.save(User.create(
                dto.getNickname(),
                dto.getName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getUserRole(),
                dto.getProfile(),
                dto.getPhoneNumber()
        ));

    }

    public PostAuthLoginResDto createAccessToken(PostAuthLoginReqDto dto){
        User user = userRepository.findByNickname(dto.getNickname())
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new UserException(UserErrorCode.NOT_MATCHED_PASSWORD);
        }

        Date now = new Date(System.currentTimeMillis());
        return PostAuthLoginResDto.of(
                Jwts.builder()
                        .claim("user_id", user.getId())
                        .claim("user_role", user.getRole())
                        .setIssuer(issuer)
                        .setIssuedAt(now)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date((now.getTime() + accessExpiration)))
                        .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS512)
                        .compact()
        );
    }

}
