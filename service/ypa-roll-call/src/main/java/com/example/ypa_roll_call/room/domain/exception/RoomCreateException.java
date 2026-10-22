package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class RoomCreateException extends ApplicationException {

    public RoomCreateException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public RoomCreateException(RoomId roomId, String message) {
        super(getDefaultMessage(roomId) + message);
    }

    public RoomCreateException(RoomId roomId, Exception cause) {
        super(getDefaultMessage(roomId), cause);
    }

    public RoomCreateException(RoomId roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId) + message, cause);
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Failed to create room: " + roomId.toString() + " ";
    }

}
