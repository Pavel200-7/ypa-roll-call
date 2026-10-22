package com.example.ypa_roll_call.room.domain.service;

import com.example.ypa_roll_call.room.application.port.out.RoomService;
import com.example.ypa_roll_call.room.domain.exception.RoomNotFoundException;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomModerator {
    private final RoomService roomService;

    public void kick(RoomId roomId, ParticipantId participantId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        roomService.kick(roomId, participantId);
    }

    public void mute(RoomId roomId, ParticipantId participantId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        roomService.mute(roomId, participantId);
    }

    public void unmute(RoomId roomId, ParticipantId participantId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        roomService.unmute(roomId, participantId);
    }
}
