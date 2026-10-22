package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantBanException extends ApplicationException {

    public ParticipantBanException(String message) {
        super(message);
    }

    public ParticipantBanException(RoomId roomId, ParticipantId participantId) {
        super(getDefaultMessage(roomId, participantId));
    }

    public ParticipantBanException(RoomId roomId, ParticipantId participantId, Exception cause) {
        super(getDefaultMessage(roomId, participantId), cause);
    }

    public ParticipantBanException(RoomId roomId, ParticipantId participantId, String message) {
            super(getDefaultMessage(roomId, participantId) + message);
    }

    private static String getDefaultMessage(RoomId roomId, ParticipantId participantId) {
        return "Failed to ban participant " + participantId.toString() + " in room: " + roomId.toString() + " ";
    }
}
