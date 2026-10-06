package com.example.ypa_roll_call.auth.data.request;

import lombok.Data;

@Data
public class LogoutRequestDto {
    private String refreshToken;
}