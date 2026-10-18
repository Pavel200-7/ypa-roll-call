package com.example.ypa_roll_call.room_operations.application.port.out;

import com.example.ypa_roll_call.room_operations.domain.exception.*;
import com.example.ypa_roll_call.room_operations.domain.model.participant.vo.ParticipantId;
import com.example.ypa_roll_call.room_operations.domain.model.room.entity.Room;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.RoomId;

public interface RoomService {
    void createRoom(Room room) throws RoomCreateException;
    void deleteRoom(RoomId roomId) throws RoomDeleteException;
    boolean roomExists(RoomId roomId) throws RoomSearchException;
    void kick(RoomId roomId, ParticipantId participantId) throws ParticipantKickException;
    boolean mute(RoomId roomId, ParticipantId participantId) throws ParticipantMuteException;
    boolean unmute(RoomId roomId, ParticipantId participantId) throws ParticipantUnmuteException;
}
