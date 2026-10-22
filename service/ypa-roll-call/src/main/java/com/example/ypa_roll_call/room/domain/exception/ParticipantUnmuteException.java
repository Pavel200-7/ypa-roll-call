package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantUnmuteException extends ApplicationException {

    public ParticipantUnmuteException(String message) {
        super(message);
    }

    public ParticipantUnmuteException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public ParticipantUnmuteException(RoomId roomId, ParticipantId participantId, Exception cause) {
        super(getDefaultMessage(roomId, participantId), cause);
    }

    public ParticipantUnmuteException(RoomId roomId, ParticipantId participantId, String message) {
        super(getDefaultMessage(roomId, participantId) + message);
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Failed to unmute participant in room: " + roomId.toString() + " ";
    }

    private static String getDefaultMessage(RoomId roomId, ParticipantId participantId) {
        return "Failed to unmute participant " + participantId.toString() + " in room: " + roomId.toString() + " ";
    }
}
