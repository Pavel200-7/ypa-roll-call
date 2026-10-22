package com.example.ypa_roll_call.room.application.usecase;

import com.example.ypa_roll_call.common.annotation.logging.annotation.LogOperation;
import com.example.ypa_roll_call.common.annotation.usecase.UseCase;
import com.example.ypa_roll_call.room.application.command.CreateRoomCommand;
import com.example.ypa_roll_call.room.application.port.out.CurrentUser;
import com.example.ypa_roll_call.room.application.port.out.TokenService;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import com.example.ypa_roll_call.room.domain.model.vo.Token;
import com.example.ypa_roll_call.room.domain.service.RoomMaster;
import com.example.ypa_roll_call.room.domain.service.RoomRegistrar;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateRoomUseCase {
    private final CurrentUser currentUser;
    private final RoomMaster roomMaster;
    private final RoomRegistrar roomRegistrar;
    private final TokenService tokenService;

    @LogOperation("CreateRoomUseCase")
    public Token execute(CreateRoomCommand command) {
        OrganizerId organizerId = OrganizerId.of(currentUser.getUserIdAsUUID());
        RoomId newRoomId = roomMaster.createRoom(organizerId);

        try {
            roomRegistrar.createRoomRecord(newRoomId, organizerId);
        } catch (Exception e) {
            log.error("Failed to register room {}, compensating", newRoomId.id(), e);
            roomMaster.deleteRoom(newRoomId);
            throw e;
        }

        ParticipantId participantId = ParticipantId.of(currentUser.getUserIdAsUUID());
        return tokenService.getAdminToken(newRoomId, participantId);
    }
}
