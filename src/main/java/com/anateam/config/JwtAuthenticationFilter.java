package com.anateam.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.anateam.service.JwtService;
import com.anateam.service.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userPhoneNumber;

        // ЛОГ 1: Проверяем, пришел ли заголовок
        System.out.println(">>> JWT FILTER: Header = " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(">>> JWT FILTER: No Bearer header found, passing to next filter");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwt = authHeader.substring(7);
            // ЛОГ 2: Токен выделен
            System.out.println(">>> JWT FILTER: Token extracted: " + jwt.substring(0, 10) + "...");

            userPhoneNumber = jwtService.extractUsername(jwt);
            // ЛОГ 3: Юзернейм извлечен
            System.out.println(">>> JWT FILTER: Username extracted: " + userPhoneNumber);

            if (userPhoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userPhoneNumber);
                
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println(">>> JWT FILTER: Success! SecurityContext set for user: " + userPhoneNumber);
                } else {
                    System.out.println(">>> JWT FILTER: Token is invalid!");
                }
            }
        } catch (Exception e) {
            // ЛОГ 4: Ошибка
            System.out.println(">>> JWT FILTER ERROR: " + e.getMessage());
            e.printStackTrace(); // Чтобы видеть полную ошибку в консоли
        }

        filterChain.doFilter(request, response);
    }
}
