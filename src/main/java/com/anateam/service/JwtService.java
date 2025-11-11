package com.anateam.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String extractUsername(String token);

    public String generateToken(UserDetails userDetails);

    public boolean isTokenValid(String token, UserDetails userDetails);
}
