package com.example.ypa_roll_call.auth.data.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutDto {
    private final String refreshToken;
}