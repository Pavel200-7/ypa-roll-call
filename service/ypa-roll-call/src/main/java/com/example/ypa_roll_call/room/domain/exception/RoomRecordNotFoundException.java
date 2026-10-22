package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ResourceNotFoundException;

import java.util.UUID;

public class RoomRecordNotFoundException extends ResourceNotFoundException {

    public RoomRecordNotFoundException(UUID roomId) {
        super(getDefaultMessage(roomId));
    }

    public RoomRecordNotFoundException(UUID roomId, String message) {
        super(getDefaultMessage(roomId) + message);
    }

    public RoomRecordNotFoundException(UUID roomId, Exception cause) {
        super(getDefaultMessage(roomId), cause);
    }

    public RoomRecordNotFoundException(UUID roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId) + message, cause);
    }

    private static String getDefaultMessage(UUID roomId) {
        return "Room record not found: " + roomId.toString() + " ";
    }
}
