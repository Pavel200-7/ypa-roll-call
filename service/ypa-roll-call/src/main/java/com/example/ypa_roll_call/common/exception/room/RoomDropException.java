package com.example.ypa_roll_call.common.exception.room;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class RoomDropException extends ApplicationException {
    public RoomDropException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()) + roomId.toString());
    }

    public RoomDropException(UUID roomId, String message) {
        super(getDefaultMessage(roomId.toString()) + message);
    }

    public RoomDropException(UUID roomId, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    public RoomDropException(UUID roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to drop room: " + roomId + " ";
    }
}
