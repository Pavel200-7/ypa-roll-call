package com.example.ypa_roll_call.auth.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private String tokenType;
}