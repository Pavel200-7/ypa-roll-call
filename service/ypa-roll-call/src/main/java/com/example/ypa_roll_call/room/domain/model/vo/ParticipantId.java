package com.example.ypa_roll_call.room.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record ParticipantId(
        UUID id
) {
    public ParticipantId {
        Objects.requireNonNull(id, "Participant id must not be null");
    }

    public static ParticipantId of(UUID id) {
        return new ParticipantId(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
