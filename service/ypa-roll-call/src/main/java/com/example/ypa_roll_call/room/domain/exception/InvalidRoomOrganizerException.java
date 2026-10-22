package com.example.ypa_roll_call.room.domain.exception;

import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public class InvalidRoomOrganizerException extends RuntimeException {

    public InvalidRoomOrganizerException(RoomId roomId, OrganizerId organizerId) {
        super(getDefaultMessage(roomId, organizerId));
    }

    public InvalidRoomOrganizerException(RoomId roomId, OrganizerId organizerId, String message) {
        super(getDefaultMessage(roomId, organizerId) + message);
    }

    public InvalidRoomOrganizerException(RoomId roomId, OrganizerId organizerId, Exception cause) {
        super(getDefaultMessage(roomId, organizerId), cause);
    }

    public InvalidRoomOrganizerException(RoomId roomId, OrganizerId organizerId, String message, Exception cause) {
        super(getDefaultMessage(roomId, organizerId) + message, cause);
    }

    private static String getDefaultMessage(RoomId roomId, OrganizerId organizerId) {
        return "Participant: " + organizerId.toString() + " is not an organizer of room: " + roomId.toString() + " ";
    }

}
