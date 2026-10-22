package com.example.ypa_roll_call.room.domain.model.vo;

import java.util.Objects;

public record Token(
        String token
) {
    public Token {
        Objects.requireNonNull(token, "Token must not be null or null");
        if (token.isBlank()) {
            throw new IllegalArgumentException("Token must not be null or blank");
        }
    }

    public static Token of(String token) {
        return new Token(token);
    }

    @Override
    public String toString() {
        return token;
    }
}
