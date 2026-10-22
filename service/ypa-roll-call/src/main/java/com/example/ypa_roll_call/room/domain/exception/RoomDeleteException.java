package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;


public class RoomDeleteException extends ApplicationException {

    public RoomDeleteException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public RoomDeleteException(RoomId roomId, String message) {
        super(getDefaultMessage(roomId) + message);
    }

    public RoomDeleteException(RoomId roomId, Exception cause) {
        super(getDefaultMessage(roomId), cause);
    }

    public RoomDeleteException(RoomId roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId) + message, cause);
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Failed to delete room: " + roomId.toString() + " ";
    }
}
