package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ResourceNotFoundException;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;


public class RoomNotFoundException extends ResourceNotFoundException {

    public RoomNotFoundException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public RoomNotFoundException(RoomId roomId, String message) {
        super(getDefaultMessage(roomId) + message);
    }

    public RoomNotFoundException(RoomId roomId, Exception cause) {
        super(getDefaultMessage(roomId), cause);
    }

    public RoomNotFoundException(RoomId roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId) + message, cause);
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Room not found: " + roomId.toString() + " ";
    }
}
