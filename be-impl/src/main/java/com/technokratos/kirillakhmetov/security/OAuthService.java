package com.technokratos.kirillakhmetov.security;

public interface OAuthService {
    String getAuthUrl();

    void processOAuthPostLogin(String code);
}
