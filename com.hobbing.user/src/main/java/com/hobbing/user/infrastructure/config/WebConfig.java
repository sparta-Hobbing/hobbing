package com.hobbing.user.infrastructure.config;

<<<<<<< HEAD
import com.hobbing.user.infrastructure.filter.CustomHeaderFilter;
=======
import com.hobbing.user.infrastructure.filter.CustomAuthenticationFilter;
>>>>>>> dev
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
<<<<<<< HEAD
=======
    public CustomAuthenticationFilter customAuthorizationFilter() {
        return new CustomAuthenticationFilter();
    }

    @Bean
>>>>>>> dev
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable());

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

<<<<<<< HEAD
        http.addFilterBefore(new CustomHeaderFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers("/auths/**").permitAll() // 인증 없이 접근 허용
                        .requestMatchers("/actuator/**").permitAll() // Actuator 인증 없이 접근 허용
//                        .requestMatchers("/users/**").permitAll()
                        .anyRequest().authenticated()                  // 나머지 요청은 인증 필요
        );

//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

=======
        http.addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers("/auths/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .anyRequest().authenticated()
        );

>>>>>>> dev
        return http.build();
    }

}
