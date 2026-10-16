package com.example.ypa_roll_call.auth.data.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenDto {
    private final String refreshToken;
}