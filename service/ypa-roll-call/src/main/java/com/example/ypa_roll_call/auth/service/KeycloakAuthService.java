package com.example.ypa_roll_call.auth.service;

import com.example.ypa_roll_call.auth.data.request.LogoutDto;
import com.example.ypa_roll_call.auth.data.request.RefreshTokenDto;
import com.example.ypa_roll_call.auth.data.response.LoginResponse;
import reactor.core.publisher.Mono;

public interface KeycloakAuthService {
    String buildAuthorizationUrl();
    Mono<LoginResponse> exchangeCodeForTokens(String code);
    Mono<LoginResponse> refreshToken(RefreshTokenDto request);
    Mono<Void> logout(LogoutDto request);
}