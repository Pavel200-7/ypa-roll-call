package com.example.ypa_roll_call.room.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record UserId(
        UUID id
) {
    public UserId {
        Objects.requireNonNull(id, "User id must not be null");
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static UserId of(UUID id) {
        return new UserId(id);
    }
}