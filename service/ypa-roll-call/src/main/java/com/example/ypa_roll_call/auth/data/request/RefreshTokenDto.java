package com.example.ypa_roll_call.auth.data.request;

import lombok.Data;

@Data
public class RefreshTokenDto {
    private final String refreshToken;
}