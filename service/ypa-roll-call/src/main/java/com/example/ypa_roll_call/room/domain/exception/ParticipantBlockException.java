package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantBlockException extends ApplicationException {

    public ParticipantBlockException(String message) {
        super(message);
    }

    public ParticipantBlockException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Failed to block participant in room: " + roomId.toString() + " ";
    }
}
