package com.example.ypa_roll_call.auth.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.keycloak")
public class SecurityConfigProperties {
    private String clientId;
    private String clientSecret;
    private String outUri;
    private String dockerUri;
    private String backendCallbackUri;
    private String frontendRedirectUri;

    public String getAuthUri() {
        return outUri + "/protocol/openid-connect/auth";
    }

    public String getTokenUri() {
        return outUri + "/protocol/openid-connect/token";
    }

    public String getLogoutUri() {
        return outUri + "/protocol/openid-connect/logout";
    }

    public String getUserInfoUri() {
        return outUri + "/protocol/openid-connect/userinfo";
    }

    public String getDockerTokenUri() {
        return dockerUri + "/protocol/openid-connect/token";
    }

    public String getDockerLogoutUri() {
        return dockerUri + "/protocol/openid-connect/logout";
    }
}