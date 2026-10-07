package com.example.ypa_roll_call.auth.data.request;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}