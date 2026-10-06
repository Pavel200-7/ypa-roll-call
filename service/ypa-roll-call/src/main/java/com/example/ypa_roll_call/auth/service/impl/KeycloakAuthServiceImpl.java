package com.example.ypa_roll_call.auth.service.impl;



import com.example.ypa_roll_call.auth.conf.SecurityConfigProperties;
import com.example.ypa_roll_call.auth.data.request.LogoutRequestDto;
import com.example.ypa_roll_call.auth.data.request.RefreshTokenRequestDto;
import com.example.ypa_roll_call.auth.data.response.LoginResponseDto;
import com.example.ypa_roll_call.auth.service.KeycloakAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAuthServiceImpl implements KeycloakAuthService {

    private final WebClient webClient;
    private final SecurityConfigProperties keycloakConfig;

    @Override
    public String buildAuthorizationUrl() {
        return UriComponentsBuilder.fromUriString(keycloakConfig.getAuthUri())
                .queryParam("client_id", keycloakConfig.getClientId())
                .queryParam("redirect_uri", keycloakConfig.getBackendCallbackUri())
                .queryParam("response_type", "code")
                .queryParam("scope", "openid profile email phone")
                .build()
                .encode()
                .toUriString();
    }

    @Override
    public Mono<LoginResponseDto> exchangeCodeForTokens(String code) {
        log.info("Exchanging code for tokens");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakConfig.getClientId());
        body.add("client_secret", keycloakConfig.getClientSecret());
        body.add("code", code);
        body.add("redirect_uri", keycloakConfig.getBackendCallbackUri());
        body.add("grant_type", "authorization_code");

        log.info("Мы ищем ответа тут {}", keycloakConfig.getDockerTokenUri());
        return webClient.post()
                .uri(keycloakConfig.getDockerTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("Keycloak error response status: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .doOnNext(errorBody -> log.error("Keycloak error body: {}", errorBody))
                            .then(Mono.error(new RuntimeException("Keycloak token exchange failed: " + response.statusCode())));
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(response -> {
                    log.info("Successfully exchanged code for tokens");
                    return LoginResponseDto.builder()
                            .accessToken((String) response.get("access_token"))
                            .refreshToken((String) response.get("refresh_token"))
                            .expiresIn((Integer) response.get("expires_in"))
                            .tokenType((String) response.get("token_type"))
                            .build();
                });
    }

    @Override
    public Mono<LoginResponseDto> refreshToken(RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();

        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            log.error("Refresh token is empty");
            return Mono.error(new IllegalArgumentException("Refresh token is empty"));
        }

        log.info("Refreshing token");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakConfig.getClientId());
        body.add("client_secret", keycloakConfig.getClientSecret());
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");

        return webClient.post()
                .uri(keycloakConfig.getDockerTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("Keycloak error response status: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .doOnNext(errorBody -> log.error("Keycloak error body: {}", errorBody))
                            .then(Mono.error(new RuntimeException("Keycloak token refresh failed: " + response.statusCode())));
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(response -> {
                    log.info("Token refreshed successfully");
                    return LoginResponseDto.builder()
                            .accessToken((String) response.get("access_token"))
                            .refreshToken((String) response.get("refresh_token"))
                            .expiresIn((Integer) response.get("expires_in"))
                            .tokenType((String) response.get("token_type"))
                            .build();
                });
    }

    @Override
    public Mono<Void> logout(LogoutRequestDto request) {
        String refreshToken = request.getRefreshToken();
        log.info("Logging out");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakConfig.getClientId());
        body.add("client_secret", keycloakConfig.getClientSecret());
        body.add("refresh_token", refreshToken);

        return webClient.post()
                .uri(keycloakConfig.getLogoutUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> log.info("Successfully logged out"))
                .onErrorResume(e -> {
                    log.error("Logout error: {}", e.getMessage());
                    return Mono.empty();
                });
    }
}
