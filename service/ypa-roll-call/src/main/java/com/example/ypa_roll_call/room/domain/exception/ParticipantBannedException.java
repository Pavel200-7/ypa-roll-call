package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantBannedException extends RuntimeException {

    public ParticipantBannedException(RoomId roomId, ParticipantId participantId) {
        super(getDefaultMessage(roomId, participantId));
    }

    public ParticipantBannedException(RoomId roomId, ParticipantId participantId, String message) {
        super(getDefaultMessage(roomId, participantId) + message);
    }

    public ParticipantBannedException(RoomId roomId, ParticipantId participantId, Exception cause) {
        super(getDefaultMessage(roomId, participantId), cause);
    }

    public ParticipantBannedException(RoomId roomId, ParticipantId participantId, String message, Exception cause) {
        super(getDefaultMessage(roomId, participantId) + message, cause);
    }

    private static String getDefaultMessage(RoomId roomId, ParticipantId participantId) {
        return "Participant: " + participantId.toString() + " is banned in room: " + roomId.toString() + " ";
    }
}
