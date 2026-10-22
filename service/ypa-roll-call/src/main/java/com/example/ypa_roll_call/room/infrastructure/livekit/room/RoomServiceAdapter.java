package com.example.ypa_roll_call.room.infrastructure.livekit.room;

import com.example.ypa_roll_call.common.annotation.logging.annotation.LogOperation;
import com.example.ypa_roll_call.room.application.port.out.RoomService;
import com.example.ypa_roll_call.room.domain.exception.*;
import com.example.ypa_roll_call.room.domain.model.entity.room.Room;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import io.livekit.server.LiveKitAPI;
import livekit.LivekitModels;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
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
            throw new RoomCreateException(roomId, e);
        }

        if (!response.isSuccessful()) {
            log.warn("Creation of room failed, cause, {}", response.message());
            throw new RoomCreateException(roomId, response.message());
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
            throw new RoomDeleteException(roomId, e);
        }

        if (!response.isSuccessful()) {
            log.warn("Dropping of roomId failed, cause, {}", response.message());
            throw new RoomDeleteException(roomId, response.message());
        }
        log.debug("The roomId {} has been dropped", roomId);
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
            log.warn("Checking if roomId {} exist failed, cause, {}", roomId, e.getMessage());
            throw new RoomSearchException(e);
        }

        if (!response.isSuccessful()) {
            log.warn("Checking if roomId {} exist failed, cause, {}", roomId, response.message());
            throw new RoomSearchException(roomId, response.message());
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
            throw new ParticipantKickException(roomId, participantId, e);
        }

        if (!response.isSuccessful()) {
            log.warn("Kicking participant {} from room {} failed, cause: {}",
                    participantId.id(), roomId.id(), response.message());
            throw new ParticipantKickException(roomId, participantId, response.message());
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
                throw new ParticipantMuteException(roomId, participantId, e);
            } else {
                throw new ParticipantUnmuteException(roomId, participantId, e);
            }
        }

        if (!response.isSuccessful()) {
            log.warn("{} participant {} in room {} failed, cause: {}",
                    isMute ? "Muting" : "Unmuting", participantId.id(), roomId.id(), response.message());
            if (isMute) {
                throw new ParticipantMuteException(roomId, participantId, response.message());
            } else {
                throw new ParticipantUnmuteException(roomId, participantId, response.message());
            }
        }

        log.debug("Participant {} in roomId {} was {}",
                participantId.id(), roomId.id(), isMute ? "muted" : "unmuted");
        return true;
    }

}
