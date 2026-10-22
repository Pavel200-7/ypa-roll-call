package com.example.ypa_roll_call.room.domain.service;

import com.example.ypa_roll_call.room.application.port.out.RoomEventPublisher;
import com.example.ypa_roll_call.room.application.port.out.RoomService;
import com.example.ypa_roll_call.room.domain.event.RoomCreated;
import com.example.ypa_roll_call.room.domain.event.RoomDeleted;
import com.example.ypa_roll_call.room.domain.model.entity.room.Room;

import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomMaster {
    private final RoomService roomService;
    private final RoomEventPublisher publisher;

    public RoomId createRoom(OrganizerId organizerId) {
        Room newRoom = Room.create(organizerId);
        roomService.createRoom(newRoom);
        publisher.publish(new RoomCreated(newRoom));
        return newRoom.getId();
    }

    public void deleteRoom(RoomId roomId) {
        roomService.deleteRoom(roomId);
        publisher.publish(new RoomDeleted(roomId));
    }
}
