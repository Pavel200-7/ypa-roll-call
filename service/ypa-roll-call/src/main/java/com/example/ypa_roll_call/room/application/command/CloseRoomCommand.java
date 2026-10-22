package com.example.ypa_roll_call.room.application.command;

import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

import java.util.Objects;

public record CloseRoomCommand(
        RoomId roomId
) {

    public CloseRoomCommand {
        Objects.requireNonNull(roomId, "roomId must not be null");
    }

    public static CloseRoomCommand of(RoomId roomId) { return new CloseRoomCommand(roomId); }
}
