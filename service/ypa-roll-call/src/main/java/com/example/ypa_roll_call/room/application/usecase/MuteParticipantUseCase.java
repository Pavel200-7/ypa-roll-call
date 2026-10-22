package com.example.ypa_roll_call.room.application.usecase;


import com.example.ypa_roll_call.common.annotation.logging.annotation.LogOperation;
import com.example.ypa_roll_call.common.annotation.usecase.UseCase;
import com.example.ypa_roll_call.room.application.command.BanParticipantCommand;
import com.example.ypa_roll_call.room.application.command.MuteParticipantCommand;
import com.example.ypa_roll_call.room.application.port.out.CurrentUser;
import com.example.ypa_roll_call.room.domain.exception.InvalidRoomOrganizerException;
import com.example.ypa_roll_call.room.domain.exception.ParticipantBanException;
import com.example.ypa_roll_call.room.domain.exception.ParticipantMuteException;
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
public class MuteParticipantUseCase {
    private final CurrentUser currentUser;
    private final RoomModerator roomModerator;
    private final RoomRegistrar roomRegistrar;
    private final RoomRestrictionRegistrar roomRestrictionRegistrar;

    @LogOperation("MuteParticipantUseCase")
    public void execute(MuteParticipantCommand command ) {
        RoomRecord roomRecord = roomRegistrar.getRoomRecord(command.roomId());
        roomRecord.ensureOrganizer(
                OrganizerId.of(currentUser.getUserIdAsUUID()));

        try {
            roomModerator.mute(command.roomId(), command.participantId());
        } catch (Exception e) {
            log.warn("Failed to mute participant: {}, in room {} cause: {}",
                    command.participantId().toString(),
                    command.roomId().toString(),
                    e.getMessage());
            throw new ParticipantMuteException(command.roomId(), command.participantId(), e);
        }

        try {
            roomRestrictionRegistrar.muteParticipant(command.roomId(), command.participantId());
        } catch (Exception e) {
            log.warn("Failed to mute participant: {}, in room {} cause: {}",
                    command.participantId().toString(),
                    command.roomId().toString(),
                    e.getMessage());
            roomModerator.unmute(command.roomId(), command.participantId());
            throw new ParticipantMuteException(command.roomId(), command.participantId(), e);
        }
    }
}
