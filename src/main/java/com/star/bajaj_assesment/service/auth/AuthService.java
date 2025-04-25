package com.star.bajaj_assesment.service.auth;

public interface AuthService {
    void setToken(String token);
    String getToken();
    boolean hasToken();
} 