package com.example.ypa_roll_call.room_operations.domain.model.room.entity;

import com.example.ypa_roll_call.room_operations.domain.model.room.vo.OrganizerId;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.RoomId;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Room {
    private final  RoomId id;
    private final  OrganizerId organizerId;

    private Room(OrganizerId organizerId) {
        this.id = new RoomId(UUID.randomUUID());
        this.organizerId = organizerId;
    }

    public static Room create(OrganizerId organizerId) {
        return new Room(organizerId);
    }
}
