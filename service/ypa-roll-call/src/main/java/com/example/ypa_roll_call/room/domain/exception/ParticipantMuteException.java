package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.common.exception.base.ApplicationException;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class ParticipantMuteException extends ApplicationException {

    public ParticipantMuteException(String message) {
        super(message);
    }

    public ParticipantMuteException(RoomId roomId) {
        super(getDefaultMessage(roomId));
    }

    public ParticipantMuteException(RoomId roomId, ParticipantId participantId, Exception cause) {
        super(getDefaultMessage(roomId, participantId), cause);
    }

    public ParticipantMuteException(RoomId roomId, ParticipantId participantId, String message) {
        super(getDefaultMessage(roomId, participantId) + message);
    }

    private static String getDefaultMessage(RoomId roomId) {
        return "Failed to mute participant in room: " + roomId.toString() + " ";
    }

    private static String getDefaultMessage(RoomId roomId, ParticipantId participantId) {
        return "Failed to mute participant " + participantId.toString() + " in room: " + roomId.toString() + " ";
    }
}
