package com.example.ypa_roll_call.room.domain.event;

import com.example.ypa_roll_call.common.domain.event.DomainEvent;
import com.example.ypa_roll_call.room.domain.model.entity.room.Room;

public record RoomCreated(
        Room room
) implements DomainEvent {}
