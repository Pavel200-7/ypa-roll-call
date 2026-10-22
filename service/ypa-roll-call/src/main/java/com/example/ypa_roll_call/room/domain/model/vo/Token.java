package com.example.ypa_roll_call.room.domain.model.vo;

import java.util.Objects;

public record Token(
        String value
) {
    public Token {
        Objects.requireNonNull(value, "Token must not be null or null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Token must not be null or blank");
        }
    }

    public static Token of(String token) {
        return new Token(token);
    }

    @Override
    public String toString() {
        return value;
    }
}
