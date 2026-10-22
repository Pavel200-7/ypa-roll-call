package com.example.ypa_roll_call.room.domain.model.vo;

import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;

import java.util.Objects;
import java.util.UUID;

public record OrganizerId(
        UUID id
) {
    public OrganizerId {
        Objects.requireNonNull(id, "Organizer id must not be null");
    }

    public static OrganizerId of(UUID id) {
        return new OrganizerId(id);
    }


    @Override
    public String toString() {
        return id.toString();
    }
}
