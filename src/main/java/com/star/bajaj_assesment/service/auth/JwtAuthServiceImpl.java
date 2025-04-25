package com.star.bajaj_assesment.service.auth;

import com.star.bajaj_assesment.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtAuthServiceImpl implements AuthService {

    private String token;

    @Override
    public void setToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new AuthenticationException("Cannot set null or empty token");
        }
        this.token = token;
        log.info("JWT token successfully set");
    }

    @Override
    public String getToken() {
        if (!hasToken()) {
            throw new AuthenticationException("No JWT token available");
        }
        return token;
    }

    @Override
    public boolean hasToken() {
        return token != null && !token.isEmpty();
    }
} 