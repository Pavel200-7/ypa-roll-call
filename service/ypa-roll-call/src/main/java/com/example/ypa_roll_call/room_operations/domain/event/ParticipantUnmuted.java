package com.example.ypa_roll_call.room_operations.domain.event;

import com.example.ypa_roll_call.common.domain.event.DomainEvent;
import com.example.ypa_roll_call.room_operations.domain.model.participant.entity.Participant;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.RoomId;

public record ParticipantUnmuted(
        RoomId roomId,
        Participant participant
) implements DomainEvent {}
