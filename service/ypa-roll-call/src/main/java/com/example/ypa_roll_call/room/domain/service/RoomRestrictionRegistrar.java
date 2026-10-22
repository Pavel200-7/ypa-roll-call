package com.example.ypa_roll_call.room.domain.service;

import com.example.ypa_roll_call.room.application.port.out.RoomRecordRepository;
import com.example.ypa_roll_call.room.domain.exception.ParticipantRecordNotFindException;
import com.example.ypa_roll_call.room.domain.exception.RoomRecordNotFoundException;
import com.example.ypa_roll_call.room.domain.model.entity.record.ParticipantRecord;
import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomRestrictionRegistrar {
    private final RoomRecordRepository repository;

    public void banParticipant(RoomId roomId, ParticipantId participantId)
            throws RoomRecordNotFoundException, ParticipantRecordNotFindException {
        RoomRecord roomRecord = repository.findById(roomId)
                .orElseThrow(() -> new RoomRecordNotFoundException(roomId.id()));
        ParticipantRecord participantRecord = roomRecord.getParticipantRecord(participantId)
                .orElseThrow(() -> new ParticipantRecordNotFindException(roomId));
        participantRecord.ban();
        repository.save(roomRecord);
    }

    public void unbanParticipant(RoomId roomId, ParticipantId participantId)
            throws RoomRecordNotFoundException, ParticipantRecordNotFindException {
        RoomRecord roomRecord = repository.findById(roomId)
                .orElseThrow(() -> new RoomRecordNotFoundException(roomId.id()));
        ParticipantRecord participantRecord = roomRecord.getParticipantRecord(participantId)
                .orElseThrow(() -> new ParticipantRecordNotFindException(roomId));
        participantRecord.unban();
        repository.save(roomRecord);
    }

    public void muteParticipant(RoomId roomId, ParticipantId participantId)
            throws RoomRecordNotFoundException, ParticipantRecordNotFindException {
        RoomRecord roomRecord = repository.findById(roomId)
                .orElseThrow(() -> new RoomRecordNotFoundException(roomId.id()));
        ParticipantRecord participantRecord = roomRecord.getParticipantRecord(participantId)
                .orElseThrow(() -> new ParticipantRecordNotFindException(roomId));
        participantRecord.mute();
        repository.save(roomRecord);
    }

    public void unmuteParticipant(RoomId roomId, ParticipantId participantId)
            throws RoomRecordNotFoundException, ParticipantRecordNotFindException {
        RoomRecord roomRecord = repository.findById(roomId)
                .orElseThrow(() -> new RoomRecordNotFoundException(roomId.id()));
        ParticipantRecord participantRecord = roomRecord.getParticipantRecord(participantId)
                .orElseThrow(() -> new ParticipantRecordNotFindException(roomId));
        participantRecord.unmute();
        repository.save(roomRecord);
    }
}
