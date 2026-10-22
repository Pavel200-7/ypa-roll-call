//package com.example.ypa_roll_call.roomold.service.impl;
//
//import com.example.ypa_roll_call.common.exception.room.*;
//import com.example.ypa_roll_call.common.annotation.annotation.logging.LogOperation;
//import com.example.ypa_roll_call.roomold.data.request.CreateRoomDto;
//import com.example.ypa_roll_call.roomold.data.response.RoomDto;
//import com.example.ypa_roll_call.roomold.mapper.RoomMapper;
//import com.example.ypa_roll_call.roomold.repository.RoomRepository;
//import com.example.ypa_roll_call.roomold.service.RoomService;
//import io.livekit.server.LiveKitAPI;
//import jakarta.transaction.Transactional;
//import livekit.LivekitModels;
//import com.example.ypa_roll_call.roomold.jpa.Room;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import retrofit2.Response;
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class RoomServiceImpl implements RoomService {
//    private final LiveKitAPI api;
//    private final RoomRepository repository;
//    private final RoomMapper mapper;
//
//    @Override
//    @Transactional
//    @LogOperation("CREATE_ROOM")
//    public RoomDto createRoom(CreateRoomDto dto) {
//        Room newRoom = Room.createRoom(dto.getAdminId());
//        repository.save(newRoom);
//
//        Response<livekit.LivekitModels.Room> response;
//        try {
//            response = api.getRoom().createRoom(newRoom.getStringId())
//                    .execute();
//        } catch (IOException e) {
//            log.warn("Creation of roomold failed, cause, {}", e.getMessage());
//            throw new RoomCreateException(newRoom.getId(), e);
//        }
//
//        if (!response.isSuccessful()) {
//            log.warn("Creation of roomold failed, cause, {}", response.message());
//            throw new RoomCreateException(newRoom.getId(), response.message());
//        }
//
//        log.debug("The roomold {} has been created", newRoom.getId());
//        return mapper.toDto(newRoom);
//    }
//
//    @Override
//    @Transactional
//    @LogOperation("DROP_ROOM")
//    public void dropRoom(UUID roomId) {
//        Response<Void> response;
//        try {
//            response = api.getRoom().deleteRoom(roomId.toString()).execute();
//        } catch (IOException e) {
//            log.warn("Dropping of roomold failed, cause, {}", e.getMessage());
//            throw new RoomDropException(roomId, e);
//        }
//
//        if (!response.isSuccessful()) {
//            log.warn("Dropping of roomold failed, cause, {}", response.message());
//            throw new RoomDropException(roomId, response.message());
//        }
//
//        if (repository.existsById(roomId)) {
//            repository.deleteById(roomId);
//        }
//        log.debug("The roomold {} has been dropped", roomId);
//    }
//
//    @Override
//    @Transactional
//    @LogOperation("DELETE_LOCAL_ROOM")
//    public void deleteLocalRoom(UUID roomId) {
//        if (repository.existsById(roomId)) {
//            repository.deleteById(roomId);
//            log.debug("Local record for roomold {} deleted", roomId);
//        }
//    }
//
//    @Override
//    @LogOperation("EXIST_ROOM")
//    public boolean roomExists(UUID roomId) {
//        Response<List<livekit.LivekitModels.Room>> response;
//        try {
//            response = api.getRoom()
//                    .listRooms(List.of(roomId.toString()))
//                    .execute();
//        } catch (IOException e) {
//            log.warn("Checking if roomold {} exist failed, cause, {}", roomId, e.getMessage());
//            throw new RoomSearchException(e);
//        }
//
//        List<livekit.LivekitModels.Room> rooms = response.body();
//
//        if (rooms == null || rooms.isEmpty()) {
//            log.debug("No rooms found for id {}", roomId);
//            return false;
//        }
//
//        log.debug("Rooms found: {}", rooms.stream()
//                .map(room -> room.getName())
//                .collect(Collectors.joining(", ", "{ ", " }")));
//        return !rooms.isEmpty();
//    }
//
//    @Override
//    @LogOperation("KICK_ALL_FROM_ROOM")
//    public void kickAll(UUID roomId) {
//        log.debug("Kicking of all participants in roomold {} started", roomId);
//
//        if (!roomExists(roomId)) {
//            log.debug("The roomold {} does not exist", roomId);
//            throw new RoomNotFoundException(roomId);
//        }
//
//        Response<List<LivekitModels.ParticipantInfo>> response;
//        try {
//            response = api.getRoom()
//                    .listParticipants(roomId.toString())
//                    .execute();
//        } catch (IOException e) {
//            log.warn("Searching participants of roomold {} failed, cause, {}", roomId, e.getMessage());
//            throw new RoomSearchException(roomId, e);
//        }
//
//        List<LivekitModels.ParticipantInfo> participants = response.body();
//        if (participants == null || participants.isEmpty()) {
//            log.debug("No participants to kick in roomold {}", roomId);
//            return;
//        }
//
//        List<Exception> failures = new ArrayList<>();
//        participants.forEach(p -> {
//                    try {
//                        api.getRoom()
//                                .removeParticipant(roomId.toString(), p.getIdentity()).execute();
//                    } catch (IOException e) {
//                        log.warn("Fail to kick participant: {}, cause:", p.getIdentity(), e.getMessage());
//                        failures.add(e);
//                    }
//                });
//
//        if (!failures.isEmpty()) {
//            log.debug("Failed to kick " + failures.size() + " of " + participants.size());
//        } else {
//            log.debug("All participants of roomold {} were kicked", roomId);
//        }
//    }
//
//    @Override
//    @LogOperation("GET_ROOM_ADMIN")
//    public UUID getRoomAdmin(UUID roomId) {
//        return repository.findById(roomId)
//                .orElseThrow(() -> new RoomNotFoundException(roomId))
//                .getAdminId();
//    }
//}
