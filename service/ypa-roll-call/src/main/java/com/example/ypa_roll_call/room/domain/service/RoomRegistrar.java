package com.example.ypa_roll_call.room.domain.service;

import com.example.ypa_roll_call.room.application.port.out.RoomRecordRepository;
import com.example.ypa_roll_call.room.domain.exception.RoomRecordNotFoundException;
import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomRegistrar {
    private final RoomRecordRepository repository;

    public void createRoomRecord(RoomId roomId, OrganizerId organizerId) {
        RoomRecord roomRecord = RoomRecord.createNew(roomId, organizerId);
        repository.save(roomRecord);
    }

    public void deleteRoomRecord(RoomId roomId) {
        repository.deleteById(roomId);
    }

    public void addRoomParticipantRecord(RoomId roomId, ParticipantId participantId) throws RoomRecordNotFoundException {
        RoomRecord roomRecord = repository.findById(roomId)
                .orElseThrow(() -> new RoomRecordNotFoundException(roomId.id()));
        roomRecord.addParticipant(participantId);
        repository.save(roomRecord);
    }

    public boolean existRoomRecord(RoomId roomId) {
        return repository.existsById(roomId);
    }

    public RoomRecord getRoomRecord(RoomId roomId) {
        return repository.findById(roomId)
                .orElseThrow(() -> new RoomRecordNotFoundException(roomId.id()));
    }
}
