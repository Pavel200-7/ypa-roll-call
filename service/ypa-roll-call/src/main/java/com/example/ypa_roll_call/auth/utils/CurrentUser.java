package com.example.ypa_roll_call.auth.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    public String getUserId() {
        return getJwt()
                .getClaimAsString(Claims.USER_ID);
    }

    public String getUserName() {
        return getJwt()
                .getClaimAsString(Claims.USER_NAME);
    }

    private Jwt getJwt() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken();
        }
        throw new IllegalStateException("No JWT token found in security context");
    }
}
