package com.example.ypa_roll_call.room.service;

import com.example.ypa_roll_call.room.data.request.CreateRoomDto;
import com.example.ypa_roll_call.room.data.response.RoomDto;

import java.io.IOException;
import java.util.UUID;

public interface RoomService {
    RoomDto createRoom(CreateRoomDto dto);
    void dropRoom(UUID roomId);
    void deleteLocalRoom(UUID roomId);
    boolean roomExists(UUID roomId);
    void kickAll(UUID roomId);
    UUID getRoomAdmin(UUID roomId);
}
