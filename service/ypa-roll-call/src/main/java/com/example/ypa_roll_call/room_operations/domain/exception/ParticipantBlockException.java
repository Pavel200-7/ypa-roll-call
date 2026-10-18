package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

public class ParticipantBlockException extends ApplicationException {

    public ParticipantBlockException(String message) {
        super(message);
    }

    public ParticipantBlockException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()));
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to block participant in room: " + roomId + " ";
    }
}
