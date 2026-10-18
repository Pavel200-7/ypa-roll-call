package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class RoomCreateException extends ApplicationException {

    public RoomCreateException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()) + roomId.toString());
    }

    public RoomCreateException(UUID roomId, String message) {
        super(getDefaultMessage(roomId.toString()) + message);
    }

    public RoomCreateException(UUID roomId, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    public RoomCreateException(UUID roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId.toString()) + message, cause);
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to create room: " + roomId + " ";
    }

}
