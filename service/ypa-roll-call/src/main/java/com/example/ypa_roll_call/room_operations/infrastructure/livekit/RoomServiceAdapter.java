package com.example.ypa_roll_call.room_operations.infrastructure.livekit;

import com.example.ypa_roll_call.common.logging.annotation.LogOperation;
import com.example.ypa_roll_call.room_operations.application.port.out.RoomService;
import com.example.ypa_roll_call.room_operations.domain.exception.*;
import com.example.ypa_roll_call.room_operations.domain.model.participant.vo.ParticipantId;
import com.example.ypa_roll_call.room_operations.domain.model.room.entity.Room;
import com.example.ypa_roll_call.room_operations.domain.model.room.vo.RoomId;
import io.livekit.server.LiveKitAPI;
import livekit.LivekitModels;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomServiceAdapter implements RoomService {
    private final LiveKitAPI api;

    @Override
    @LogOperation("CREATE_ROOM")
    public void createRoom(Room room) throws RoomCreateException {
        RoomId roomId = room.getId();
        Response<LivekitModels.Room> response;
        try {
            response = api.getRoom().createRoom(roomId.id().toString())
                    .execute();
        } catch (IOException e) {
            log.warn("Creation of room failed, cause, {}", e.getMessage());
            throw new RoomCreateException(roomId.id(), e);
        }

        if (!response.isSuccessful()) {
            log.warn("Creation of room failed, cause, {}", response.message());
            throw new RoomCreateException(roomId.id(), response.message());
        }

        log.debug("The room {} has been created", roomId.id());
    }

    @Override
    @LogOperation("DELETE_ROOM")
    public void deleteRoom(RoomId roomId) throws RoomDeleteException {
        Response<Void> response;
        try {
            response = api.getRoom().deleteRoom(roomId.id().toString()).execute();
        } catch (IOException e) {
            log.warn("Dropping of room failed, cause, {}", e.getMessage());
            throw new RoomDeleteException(roomId.id(), e);
        }

        if (!response.isSuccessful()) {
            log.warn("Dropping of room failed, cause, {}", response.message());
            throw new RoomDeleteException(roomId.id(), response.message());
        }
        log.debug("The room {} has been dropped", roomId);
    }

    @Override
    @LogOperation("EXIST_ROOM")
    public boolean roomExists(RoomId roomId) throws RoomSearchException {
        Response<List<livekit.LivekitModels.Room>> response;
        try {
            response = api.getRoom()
                    .listRooms(List.of(roomId.id().toString()))
                    .execute();
        } catch (IOException e) {
            log.warn("Checking if room {} exist failed, cause, {}", roomId, e.getMessage());
            throw new RoomSearchException(e);
        }

        if (!response.isSuccessful()) {
            log.warn("Checking if room {} exist failed, cause, {}", roomId, response.message());
            throw new RoomSearchException(roomId.id(), response.message());
        }

        List<livekit.LivekitModels.Room> rooms = response.body();

        if (rooms == null || rooms.isEmpty()) {
            log.debug("No rooms found for id {}", roomId);
            return false;
        }

        log.debug("Rooms found: {}", rooms.stream()
                .map(room -> room.getName())
                .collect(Collectors.joining(", ", "{ ", " }")));
        return !rooms.isEmpty();
    }

    @Override
    @LogOperation("KICK_PARTICIPANT")
    public void kick(RoomId roomId, ParticipantId participantId) throws ParticipantKickException {
        Response<Void> response;
        try {
            response = api.getRoom()
                    .removeParticipant(roomId.id().toString(), participantId.id().toString())
                    .execute();
        } catch (IOException e) {
            log.warn("Kicking participant {} from room {} failed, cause: {}",
                    participantId.id(), roomId.id(), e.getMessage());
            throw new ParticipantKickException(roomId.id(), participantId.id(), e);
        }

        if (!response.isSuccessful()) {
            log.warn("Kicking participant {} from room {} failed, cause: {}",
                    participantId.id(), roomId.id(), response.message());
            throw new ParticipantKickException(roomId.id(), participantId.id(), response.message());
        }

        log.debug("Participant {} was kicked from room {}", participantId.id(), roomId.id());
    }

    @Override
    @LogOperation("MUTE_PARTICIPANT")
    public boolean mute(RoomId roomId, ParticipantId participantId) throws ParticipantMuteException {
        return updateCanPublish(roomId, participantId, false, true);
    }

    @Override
    @LogOperation("UNMUTE_PARTICIPANT")
    public boolean unmute(RoomId roomId, ParticipantId participantId) throws ParticipantUnmuteException {
        return updateCanPublish(roomId, participantId, true, false);
    }

    private boolean updateCanPublish(RoomId roomId, ParticipantId participantId,
                                     boolean canPublish, boolean isMute) {


        LivekitModels.ParticipantPermission  permission = LivekitModels.ParticipantPermission.newBuilder()
                .setCanPublish(canPublish)
                .build();

        Response<LivekitModels.ParticipantInfo> response;
        try {
            response = api.getRoom().updateParticipant(
                    roomId.id().toString(),
                    participantId.id().toString(),
                    null,
                    null,
                    permission,
                    null
            ).execute();
        } catch (IOException e) {
            log.warn("{} participant {} in room {} failed, cause: {}",
                    isMute ? "Muting" : "Unmuting", participantId.id(), roomId.id(), e.getMessage());
            if (isMute) {
                throw new ParticipantMuteException(roomId.id(), participantId.id(), e);
            } else {
                throw new ParticipantUnmuteException(roomId.id(), participantId.id(), e);
            }
        }

        if (!response.isSuccessful()) {
            log.warn("{} participant {} in room {} failed, cause: {}",
                    isMute ? "Muting" : "Unmuting", participantId.id(), roomId.id(), response.message());
            if (isMute) {
                throw new ParticipantMuteException(roomId.id(), participantId.id(), response.message());
            } else {
                throw new ParticipantUnmuteException(roomId.id(), participantId.id(), response.message());
            }
        }

        log.debug("Participant {} in room {} was {}",
                participantId.id(), roomId.id(), isMute ? "muted" : "unmuted");
        return true;
    }

}
