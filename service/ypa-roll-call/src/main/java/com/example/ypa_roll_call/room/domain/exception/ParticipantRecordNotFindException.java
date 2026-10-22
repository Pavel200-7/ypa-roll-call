package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantRecordNotFindException extends RuntimeException {

    public ParticipantRecordNotFindException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public ParticipantRecordNotFindException(RoomId roomId, String message) {
        super(getDefaultMessage(roomId) + message);
    }

    public ParticipantRecordNotFindException(RoomId roomId, Exception cause) {
        super(getDefaultMessage(roomId), cause);
    }

    public ParticipantRecordNotFindException(RoomId roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId) + message, cause);
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Participant record not found: " + roomId.toString() + " ";
    }
}