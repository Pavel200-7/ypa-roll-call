package com.example.ypa_roll_call.common.exception.room;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class ParticipantKickException extends ApplicationException {
    public ParticipantKickException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()) + roomId.toString());
    }

    public ParticipantKickException(UUID roomId, String message) {
        super(getDefaultMessage(roomId.toString()) + message);
    }

    public ParticipantKickException(UUID roomId, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    public ParticipantKickException(UUID roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId.toString()) + message, cause);
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to kick participant from room: " + roomId + " ";
    }
}
