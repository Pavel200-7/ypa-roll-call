package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class ParticipantMuteException extends ApplicationException {

    public ParticipantMuteException(String message) {
        super(message);
    }

    public ParticipantMuteException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()));
    }

    public ParticipantMuteException(UUID roomId, UUID participantId, Exception cause) {
        super(getDefaultMessage(roomId.toString(), participantId.toString()), cause);
    }

    public ParticipantMuteException(UUID roomId, UUID participantId, String message) {
        super(getDefaultMessage(roomId.toString(), participantId.toString()) + message);
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to mute participant in room: " + roomId + " ";
    }

    private static String getDefaultMessage(String roomId, String participantId) {
        return "Failed to mute participant " + participantId + " in room: " + roomId + " ";
    }
}
