package com.example.ypa_roll_call.room.application.port.out;

import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;

import java.util.Optional;

public interface RoomRecordRepository {
    RoomRecord save(RoomRecord roomRecord);
    Optional<RoomRecord> findById(RoomId roomId);
    boolean existsById(RoomId roomId);
    void deleteById(RoomId roomId);
}
