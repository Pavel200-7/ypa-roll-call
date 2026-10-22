package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ResourceNotFoundException;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantRecordNotFoundException extends ResourceNotFoundException {

    public ParticipantRecordNotFoundException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public ParticipantRecordNotFoundException(RoomId roomId, String message) {
        super(getDefaultMessage(roomId) + message);
    }

    public ParticipantRecordNotFoundException(RoomId roomId, Exception cause) {
        super(getDefaultMessage(roomId), cause);
    }

    public ParticipantRecordNotFoundException(RoomId roomId, String message, Exception cause) {
        super(getDefaultMessage(roomId) + message, cause);
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Participant record not found: " + roomId.toString() + " ";
    }
}