package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantUnbanException extends ApplicationException {

    public ParticipantUnbanException(String message) {
        super(message);
    }

    public ParticipantUnbanException(RoomId roomId, ParticipantId participantId) {
        super(getDefaultMessage(roomId, participantId));
    }

    public ParticipantUnbanException(RoomId roomId, ParticipantId participantId, Exception cause) {
        super(getDefaultMessage(roomId, participantId), cause);
    }

    public ParticipantUnbanException(RoomId roomId, ParticipantId participantId, String message) {
        super(getDefaultMessage(roomId, participantId) + message);
    }

    private static String getDefaultMessage(RoomId roomId, ParticipantId participantId) {
        return "Failed to unban participant " + participantId.toString() + " in room: " + roomId.toString() + " ";
    }
}
