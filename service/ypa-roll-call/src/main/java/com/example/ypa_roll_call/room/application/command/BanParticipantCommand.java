package com.example.ypa_roll_call.room.application.command;

import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

import java.util.Objects;

public record BanParticipantCommand(
        RoomId roomId,
        ParticipantId participantId
) {

    public BanParticipantCommand {
        Objects.requireNonNull(roomId, "roomId must not be null");
        Objects.requireNonNull(participantId, "participantId must not be null");

    }

    public static BanParticipantCommand of(RoomId roomId, ParticipantId participantId) { return new BanParticipantCommand(roomId, participantId); }
}
