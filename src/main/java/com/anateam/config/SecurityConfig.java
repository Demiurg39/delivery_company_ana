package com.anateam.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.anateam.service.UserDetailServiceImpl;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =
            new DaoAuthenticationProvider(userDetailServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http
            // NOTE: disable csrf
            .csrf(csrf -> csrf.disable())

            // NOTE: autorization rules
            .authorizeHttpRequests(
                authz
                -> authz
                       // NOTE: white-list
                       .requestMatchers(
                           "/api/auth/**",     // NOTE: registration and login
                           "/swagger-ui.html", // NOTE: Swagger ui
                           "/swagger-ui/**",   // NOTE: Still swagger
                           "/v3/api-docs/**"   // NOTE: OpenAPI
                           )
                       .permitAll()
                       // NOTE: other requests must be authenticated
                       .anyRequest()
                       .authenticated())

            .sessionManagement(session
                               -> session.sessionCreationPolicy(
                                   SessionCreationPolicy.STATELESS));
        // TODO: add jwt filter
        // .addFilterBefore(jwtAuthFilter,
        // UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
