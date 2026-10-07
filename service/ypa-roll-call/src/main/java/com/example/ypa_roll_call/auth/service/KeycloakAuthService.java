package com.example.ypa_roll_call.auth.service;

import com.example.ypa_roll_call.auth.data.request.LogoutRequest;
import com.example.ypa_roll_call.auth.data.request.RefreshTokenRequest;
import com.example.ypa_roll_call.auth.data.response.LoginResponse;
import reactor.core.publisher.Mono;

public interface KeycloakAuthService {
    String buildAuthorizationUrl();
    Mono<LoginResponse> exchangeCodeForTokens(String code);
    Mono<LoginResponse> refreshToken(RefreshTokenRequest request);
    Mono<Void> logout(LogoutRequest request);
}