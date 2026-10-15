package com.example.ypa_roll_call.common.exception.room;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

public class ParticipantBlockedException extends AccessDeniedException {
    public ParticipantBlockedException(String message) {
        super(message);
    }
    public ParticipantBlockedException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()));
    }

    private static String getDefaultMessage(String roomId) {
        return "You are blocked from this room: " + roomId + " ";
    }

}
