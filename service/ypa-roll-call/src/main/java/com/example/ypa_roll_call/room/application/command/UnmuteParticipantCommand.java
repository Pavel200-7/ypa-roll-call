package com.example.ypa_roll_call.room.application.command;

import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

import java.util.Objects;

public record UnmuteParticipantCommand(
        RoomId roomId,
        ParticipantId participantId
) {

    public UnmuteParticipantCommand {
        Objects.requireNonNull(roomId, "roomId must not be null");
        Objects.requireNonNull(participantId, "participantId must not be null");

    }

    public static UnmuteParticipantCommand of(RoomId roomId, ParticipantId participantId) { return new UnmuteParticipantCommand(roomId, participantId); }
}