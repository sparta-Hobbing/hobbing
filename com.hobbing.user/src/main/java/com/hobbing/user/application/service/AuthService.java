package com.hobbing.user.application.service;

import com.hobbing.user.application.dto.response.PostAuthLoginResDto;
import com.hobbing.user.application.dto.response.UserDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, UserDto> userRedisTemplate;
    private final ValueOperations<String, UserDto> userListOps;
    @Value("${spring.application.name}")
    private String issuer;
    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;
    @Value("${service.jwt.secret-key}")
    private String secretKey;

    public AuthService(
            UserRepository userRepository, PasswordEncoder passwordEncoder,
            RedisTemplate<String, UserDto> userRedisTemplate

    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRedisTemplate = userRedisTemplate;
        this.userListOps = this.userRedisTemplate.opsForValue();
    }

    @Transactional
    public void createUser(PostAuthSignupReqDto dto){
        User user = userRepository.findByNickname(dto.getNickname())
                .orElse(null);

        if(user != null){
            throw new UserException(UserErrorCode.DUPLICATED_USER_ERROR);
        }

        userRepository.save(User.create(
                dto.getNickname(),
                dto.getName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getUserRole(),
                dto.getProfile(),
                dto.getPhoneNumber()
        ));
    }

//    @CacheEvict(cacheNames = "userAllCache", allEntries = true)
    public PostAuthLoginResDto createAccessToken(PostAuthLoginReqDto dto){
        //아이디 존재하는지 조회
        User user = userRepository.findByNickname(dto.getNickname())
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_EXISTED_USER_ERROR));

        //패스워드 일치 확인
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new UserException(UserErrorCode.NOT_MATCHED_PASSWORD);
        }

//        userListOps.set("userCache::"+user.getId(), UserDto.fromEntity(user));

        //accesstoken 발급
        Date now = new Date(System.currentTimeMillis());
        return PostAuthLoginResDto.of(
                Jwts.builder()
                        .claim("user_id", user.getId())
                        .claim("user_role", user.getRole())
                        .setIssuer(issuer)
                        .setIssuedAt(now)
                        .setExpiration(new Date((now.getTime() + accessExpiration)))
                        .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS512)
                        .compact()
        );
    }

}