package com.example.ypa_roll_call.room.domain.event;

import com.example.ypa_roll_call.common.domain.event.DomainEvent;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public record RoomDeleted(
        RoomId roomId
) implements DomainEvent {}
