package com.example.ypa_roll_call.room_operations.domain.event;

import com.example.ypa_roll_call.common.domain.event.DomainEvent;
import com.example.ypa_roll_call.room_operations.domain.model.room.entity.Room;

public record RoomCreated(
        Room room
) implements DomainEvent {}
