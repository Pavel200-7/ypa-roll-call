package com.example.ypa_roll_call.auth.service;

import com.example.ypa_roll_call.auth.data.request.LogoutRequestDto;
import com.example.ypa_roll_call.auth.data.request.RefreshTokenRequestDto;
import com.example.ypa_roll_call.auth.data.response.LoginResponseDto;
import reactor.core.publisher.Mono;

public interface KeycloakAuthService {
    String buildAuthorizationUrl();
    Mono<LoginResponseDto> exchangeCodeForTokens(String code);
    Mono<LoginResponseDto> refreshToken(RefreshTokenRequestDto request);
    Mono<Void> logout(LogoutRequestDto request);
}