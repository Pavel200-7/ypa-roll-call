package com.example.ypa_roll_call.auth.controller;

import com.example.ypa_roll_call.auth.data.request.LogoutRequest;
import com.example.ypa_roll_call.auth.data.request.RefreshTokenRequest;
import com.example.ypa_roll_call.auth.data.response.LoginResponse;
import com.example.ypa_roll_call.auth.service.KeycloakAuthService;
import com.example.ypa_roll_call.auth.conf.SecurityConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakAuthService keycloakAuthService;
    private final SecurityConfigProperties securityConfig;

    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize() {
        String authUrl = keycloakAuthService.buildAuthorizationUrl();
        log.info("Redirecting to Keycloak login: {}", authUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authUrl))
                .build();
    }

    @GetMapping("/callback")
    public Mono<ResponseEntity<Void>> callback(@RequestParam("code") String code) {
        log.info("Received authorization code: {}", code);

        return keycloakAuthService.exchangeCodeForTokens(code)
                .flatMap(loginResponse -> {
                    String accessToken = loginResponse.getAccessToken();
                    String refreshToken = loginResponse.getRefreshToken();

                    log.info(securityConfig.getFrontendRedirectUri());
                    String redirectWithTokens = securityConfig.getFrontendRedirectUri() +
                            "#access_token=" + accessToken +
                            "&refresh_token=" + refreshToken;

                    log.info(redirectWithTokens);

                    log.info("Successfully authenticated, redirecting to frontend");
                    return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(redirectWithTokens))
                            .<Void>build());
                })
                .onErrorResume(e -> {
                    log.error("Failed to exchange code for tokens: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(securityConfig.getFrontendRedirectUri() + "?error=auth_failed"))
                            .<Void>build());
                });
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<LoginResponse>> refresh(@RequestBody RefreshTokenRequest request) {
        return keycloakAuthService.refreshToken(request)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    log.error("Failed to refresh token: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                });
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity<Void>> logout(@RequestBody LogoutRequest request) {
        return keycloakAuthService.logout(request)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .onErrorResume(e -> {
                    log.error("Logout error: {}", e.getMessage());
                    return Mono.just(ResponseEntity.ok().build());
                });
    }
}