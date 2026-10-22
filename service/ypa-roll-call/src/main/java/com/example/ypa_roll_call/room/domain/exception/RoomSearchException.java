package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

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

    public RoomSearchException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public RoomSearchException(RoomId roomId, String message) {
        super(getDefaultMessage(roomId) + message);
    }

    public RoomSearchException(RoomId roomId, Exception cause) {
        super(getDefaultMessage(roomId), cause);
    }

    public RoomSearchException(RoomId roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId) + message, cause);
    }

    private static String getDefaultMessage() {
        return "Failed to search rooms ";
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Failed to search roomId: " + roomId.toString() + " ";
    }
}
