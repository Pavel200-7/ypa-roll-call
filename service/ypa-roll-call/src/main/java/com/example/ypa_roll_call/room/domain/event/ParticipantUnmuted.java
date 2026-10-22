package com.example.ypa_roll_call.room.domain.event;

import com.example.ypa_roll_call.common.domain.event.DomainEvent;
import com.example.ypa_roll_call.room.domain.model.entity.room.Participant;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public record ParticipantUnmuted(
        RoomId roomId,
        Participant participant
) implements DomainEvent {}
