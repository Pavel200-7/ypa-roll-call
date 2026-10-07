package com.example.ypa_roll_call.room.service;

import com.example.ypa_roll_call.common.data.RoomOptions;

import java.io.IOException;

public interface RoomService {
    void createRoom(String roomId, RoomOptions options) throws IOException;
    void dropRoom(String roomId) throws IOException;
    boolean roomExists(String roomId) throws IOException;
    void kickAll(String roomId) throws IOException;
}
