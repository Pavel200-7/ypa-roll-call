package com.example.ypa_roll_call.auth.data.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}