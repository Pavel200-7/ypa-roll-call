package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ResourceNotFoundException;

import java.util.UUID;

public class RoomNotFoundException extends ResourceNotFoundException {

    public RoomNotFoundException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()) + roomId.toString());
    }

    public RoomNotFoundException(UUID roomId, String message) {
        super(getDefaultMessage(roomId.toString()) + message);
    }

    public RoomNotFoundException(UUID roomId, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    public RoomNotFoundException(UUID roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId.toString()) + message, cause);
    }

    private static String getDefaultMessage(String roomId) {
        return "Room not found: " + roomId + " ";
    }
}
