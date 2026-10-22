package com.example.ypa_roll_call.room.application.usecase;

import com.example.ypa_roll_call.common.annotation.logging.annotation.LogOperation;
import com.example.ypa_roll_call.common.annotation.usecase.UseCase;
import com.example.ypa_roll_call.room.application.command.CloseRoomCommand;
import com.example.ypa_roll_call.room.application.port.out.CurrentUser;
import com.example.ypa_roll_call.room.domain.exception.InvalidRoomOrganizerException;
import com.example.ypa_roll_call.room.domain.exception.RoomDeleteException;
import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.service.RoomMaster;
import com.example.ypa_roll_call.room.domain.service.RoomRegistrar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CloseRoomUseCase {
    private final CurrentUser currentUser;
    private final RoomMaster roomMaster;
    private final RoomRegistrar roomRegistrar;

    @LogOperation("CloseRoomUseCase")
    public void execute(CloseRoomCommand command) {
        RoomRecord roomRecord = roomRegistrar.getRoomRecord(command.roomId());
        roomRecord.ensureOrganizer(
                OrganizerId.of(currentUser.getUserIdAsUUID()));

        try {
            roomMaster.deleteRoom(command.roomId());
        } catch (Exception e) {
            log.warn("Failed to delete room. case: {}", e.getMessage());
            throw new RoomDeleteException(command.roomId(), e);
        }

        try {
            roomRegistrar.deleteRoomRecord(command.roomId());
        } catch (Exception e) {
            log.warn("Failed to delete room {}, cause: {}", command.roomId().id().toString(), e.getMessage());
            throw new RoomDeleteException(command.roomId(), e);
        }
    }
}
