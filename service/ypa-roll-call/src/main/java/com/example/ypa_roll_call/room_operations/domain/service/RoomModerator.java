package com.example.ypa_roll_call.room_operations.domain.service;

import com.example.ypa_roll_call.room_operations.application.port.out.RoomService;
import com.example.ypa_roll_call.room_operations.domain.exception.RoomNotFoundException;
import com.example.ypa_roll_call.room_operations.domain.model.participant.vo.ParticipantId;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.RoomId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomModerator {
    private final RoomService roomService;

    void kick(RoomId roomId, ParticipantId participantId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId.id());
        }
        roomService.kick(roomId, participantId);
    }

    void mute(RoomId roomId, ParticipantId participantId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId.id());
        }
        roomService.mute(roomId, participantId);
    }

    void unmute(RoomId roomId, ParticipantId participantId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId.id());
        }
        roomService.unmute(roomId, participantId);
    }
}
