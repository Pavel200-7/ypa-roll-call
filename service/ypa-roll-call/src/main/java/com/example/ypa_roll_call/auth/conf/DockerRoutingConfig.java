package com.example.ypa_roll_call.auth.conf;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Profile("docker")
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DockerRoutingConfig {
    private final SecurityConfigProperties properties;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                ClientRegistration.withRegistrationId("keycloak")
                        .clientId(properties.getClientId())
                        .clientSecret(properties.getClientSecret())
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .redirectUri(properties.getOutUri().concat("/login/oauth2/code/keycloak"))
                        .scope("openid", "profile", "email")
                        .authorizationUri(properties.getOutUri().concat("/protocol/openid-connect/auth"))
                        .tokenUri(properties.getDockerUri().concat("/protocol/openid-connect/token"))
                        .jwkSetUri(properties.getDockerUri().concat("/protocol/openid-connect/certs"))
                        .userInfoUri(properties.getDockerUri().concat("/protocol/openid-connect/userinfo"))
                        .userNameAttributeName("sub")
                        .clientName("Keycloak")
                        .build()
        );
    }
}