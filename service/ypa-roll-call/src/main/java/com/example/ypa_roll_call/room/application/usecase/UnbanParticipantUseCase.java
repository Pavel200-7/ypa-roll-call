package com.example.ypa_roll_call.room.application.usecase;

import com.example.ypa_roll_call.common.annotation.logging.annotation.LogOperation;
import com.example.ypa_roll_call.common.annotation.usecase.UseCase;
import com.example.ypa_roll_call.room.application.command.UnbanParticipantCommand;
import com.example.ypa_roll_call.room.application.port.out.CurrentUser;
import com.example.ypa_roll_call.room.domain.exception.InvalidRoomOrganizerException;
import com.example.ypa_roll_call.room.domain.exception.ParticipantUnbanException;
import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.service.RoomModerator;
import com.example.ypa_roll_call.room.domain.service.RoomRegistrar;
import com.example.ypa_roll_call.room.domain.service.RoomRestrictionRegistrar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class UnbanParticipantUseCase {
    private final CurrentUser currentUser;
    private final RoomRegistrar roomRegistrar;
    private final RoomRestrictionRegistrar roomRestrictionRegistrar;


    @LogOperation("UnbanParticipantUseCase")
    public void execute(UnbanParticipantCommand command ) {
        RoomRecord roomRecord = roomRegistrar.getRoomRecord(command.roomId());
        roomRecord.ensureOrganizer(
                OrganizerId.of(currentUser.getUserIdAsUUID()));

        try {
            roomRestrictionRegistrar.unbanParticipant(command.roomId(), command.participantId());
        } catch (Exception e) {
            log.warn("Failed to unban participant: {}, in room {} cause: {}",
                    command.participantId().toString(),
                    command.roomId().toString(),
                    e.getMessage());
            throw new ParticipantUnbanException(command.roomId(), command.participantId(), e);
        }
    }
}