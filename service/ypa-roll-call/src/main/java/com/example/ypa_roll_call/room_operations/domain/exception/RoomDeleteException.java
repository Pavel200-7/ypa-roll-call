package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class RoomDeleteException extends ApplicationException {

    public RoomDeleteException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()) + roomId.toString());
    }

    public RoomDeleteException(UUID roomId, String message) {
        super(getDefaultMessage(roomId.toString()) + message);
    }

    public RoomDeleteException(UUID roomId, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    public RoomDeleteException(UUID roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to delete room: " + roomId + " ";
    }
}
