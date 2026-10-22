package com.example.ypa_roll_call.room.domain.model.entity.room;

import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Room {
    private final RoomId id;
    private final OrganizerId organizerId;

    private Room(OrganizerId organizerId) {
        this.id = new RoomId(UUID.randomUUID());
        this.organizerId = organizerId;
    }

    public static Room create(OrganizerId organizerId) {
        return new Room(organizerId);
    }
}
