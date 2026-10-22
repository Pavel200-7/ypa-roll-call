package com.example.ypa_roll_call.room.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record RoomId(
        UUID id
) {
    public RoomId {
        Objects.requireNonNull(id, "Room id must not be null");
    }

    public static RoomId of(UUID id) {
        return new RoomId(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
