package com.example.ypa_roll_call.room_operations.domain.service;

import com.example.ypa_roll_call.room_operations.application.port.out.RoomEventPublisher;
import com.example.ypa_roll_call.room_operations.application.port.out.RoomService;
import com.example.ypa_roll_call.room_operations.domain.event.RoomCreated;
import com.example.ypa_roll_call.room_operations.domain.event.RoomDeleted;
import com.example.ypa_roll_call.room_operations.domain.exception.RoomNotFoundException;
import com.example.ypa_roll_call.room_operations.domain.model.room.entity.Room;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.OrganizerId;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.RoomId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomMaster {
    private final RoomService roomService;
    private final RoomEventPublisher publisher;

    void createRoom(OrganizerId organizerId) {
        Room newRoom = Room.create(organizerId);
        roomService.createRoom(newRoom);
        publisher.publish(new RoomCreated(newRoom));
    }

    void deleteRoom(RoomId roomId) {
        roomService.deleteRoom(roomId);
        publisher.publish(new RoomDeleted(roomId));
    }
}
