package com.example.ypa_roll_call.room.service.impl;

import com.example.ypa_roll_call.room.service.RoomService;
import com.example.ypa_roll_call.common.data.RoomOptions;
import io.livekit.server.LiveKitAPI;
import livekit.LivekitModels;
import livekit.LivekitModels.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final LiveKitAPI api;

    @Override
    public void createRoom(String roomId, RoomOptions options) throws IOException {
        if (roomExists(roomId)) {
            log.info("The room already exists");
            return;
        }

        Response<Room> response = api.getRoom().createRoom(roomId)
                .execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Failed to create room " + roomId +": " + response.message());
        }

        log.info("The room {} has been created", roomId);
    }

    @Override
    public void dropRoom(String roomId) throws IOException {
        Response<Void> response = api.getRoom().deleteRoom(roomId)
                .execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Failed to drop room " + roomId +": " + response.message());
        }

        log.info("The room {} has been dropped", roomId);
    }

    @Override
    public boolean roomExists(String roomId) throws IOException {
        List<Room> rooms = api.getRoom()
                .listRooms(List.of(roomId))
                .execute()
                .body();
        log.info("Rooms found: {}", rooms.stream()
                .map(room -> room.getName())
                .collect(Collectors.joining(", ", "{ ", " }")));
        return !rooms.isEmpty();
    }

    @Override
    public void kickAll(String roomId) throws IOException {
        if (!roomExists(roomId)) {
            log.info("The room does not exist");
            return;
        }

        List<LivekitModels.ParticipantInfo> participants = api.getRoom()
                .listParticipants(roomId)
                .execute()
                .body();

        if (participants == null || participants.isEmpty()) {
            log.info("No participants to kick in room {}", roomId);
            return;
        }

        participants.stream()
                .forEach(p -> {
                    try {
                        api.getRoom()
                                .removeParticipant(roomId, p.getIdentity()).execute();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        log.info("Every participant of room {} were kicked", roomId);
    }

}
