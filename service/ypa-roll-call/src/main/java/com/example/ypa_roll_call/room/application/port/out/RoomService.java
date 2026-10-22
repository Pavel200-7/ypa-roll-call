package com.example.ypa_roll_call.room.application.port.out;

import com.example.ypa_roll_call.room.domain.exception.*;
import com.example.ypa_roll_call.room.domain.model.entity.room.Room;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

public interface RoomService {
    void createRoom(Room room) throws RoomCreateException;
    void deleteRoom(RoomId roomId) throws RoomDeleteException;
    boolean roomExists(RoomId roomId) throws RoomSearchException;
    void kick(RoomId roomId, ParticipantId participantId) throws ParticipantKickException;
    boolean mute(RoomId roomId, ParticipantId participantId) throws ParticipantMuteException;
    boolean unmute(RoomId roomId, ParticipantId participantId) throws ParticipantUnmuteException;
}
