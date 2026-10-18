package com.example.ypa_roll_call.room_operations.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;

import java.util.UUID;

public class RoomSearchException extends ApplicationException {

    public RoomSearchException() {
        super(getDefaultMessage());
    }

    public RoomSearchException(String message) {
        super(getDefaultMessage() + message);
    }

    public RoomSearchException(Exception cause) {
        super(getDefaultMessage(), cause);
    }

    public RoomSearchException(String message, Exception cause) {
        super(getDefaultMessage() + message, cause);
    }

    public RoomSearchException(UUID roomId) {
        super(getDefaultMessage(roomId.toString()));
    }

    public RoomSearchException(UUID roomId, String message) {
        super(getDefaultMessage(roomId.toString()) + message);
    }

    public RoomSearchException(UUID roomId, Exception cause) {
        super(getDefaultMessage(roomId.toString()), cause);
    }

    public RoomSearchException(UUID roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId.toString()) + message, cause);
    }

    private static String getDefaultMessage() {
        return "Failed to search rooms ";
    }

    private static String getDefaultMessage(String roomId) {
        return "Failed to search room: " + roomId + " ";
    }
}
