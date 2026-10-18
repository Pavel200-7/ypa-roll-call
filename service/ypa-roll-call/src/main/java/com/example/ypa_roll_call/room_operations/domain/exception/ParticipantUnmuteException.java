package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class ParticipantUnmuteException extends ApplicationException {

    public ParticipantUnmuteException(String message) {
        super(message);
    }

    public ParticipantUnmuteException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()));
    }

    public ParticipantUnmuteException(UUID roomId, UUID participantId, Exception cause) {
        super(getDefaultMessage(roomId.toString(), participantId.toString()), cause);
    }

    public ParticipantUnmuteException(UUID roomId, UUID participantId, String message) {
        super(getDefaultMessage(roomId.toString(), participantId.toString()) + message);
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to unmute participant in room: " + roomId + " ";
    }

    private static String getDefaultMessage(String roomId, String participantId) {
        return "Failed to unmute participant " + participantId + " in room: " + roomId + " ";
    }
}
