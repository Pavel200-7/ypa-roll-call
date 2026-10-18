package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class ParticipantKickException extends ApplicationException {

    public ParticipantKickException(String message) {
        super(message);
    }

    public ParticipantKickException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()));
    }

    public ParticipantKickException(UUID roomId, UUID participantId, Exception cause) {
        super(getDefaultMessage(roomId.toString(), participantId.toString()), cause);
    }

    public ParticipantKickException(UUID roomId, UUID participantId, String message) {
        super(getDefaultMessage(roomId.toString(), participantId.toString()) + message);
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to kick participant in room: " + roomId + " ";
    }

    private static String getDefaultMessage(String roomId, String participantId) {
        return "Failed to kick participant " + participantId + " in room: " + roomId + " ";
    }
}
