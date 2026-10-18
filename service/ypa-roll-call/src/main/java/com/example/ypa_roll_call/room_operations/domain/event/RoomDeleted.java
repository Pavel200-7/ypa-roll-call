package com.example.ypa_roll_call.room_operations.domain.event;

import com.example.ypa_roll_call.common.domain.event.DomainEvent;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.RoomId;

public record RoomDeleted(
        RoomId roomId
) implements DomainEvent {}
