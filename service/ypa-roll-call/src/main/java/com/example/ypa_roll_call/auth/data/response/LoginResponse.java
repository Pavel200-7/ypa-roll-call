package com.example.ypa_roll_call.auth.data.response;

import lombok.Data;

@Data
public class LoginResponse {
    private final String accessToken;
    private final String refreshToken;
    private final Integer expiresIn;
    private final String tokenType;
}