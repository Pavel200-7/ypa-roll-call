package com.example.ypa_roll_call.room.infrastructure.persistence.mapper;

import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.infrastructure.persistence.jpa.RoomRecordJpaEntity;

public interface RoomRecordMapper {
    RoomRecordJpaEntity toJpa(RoomRecord domain);
    void updateJpa(RoomRecord domain, RoomRecordJpaEntity target);
    RoomRecord toDomain(RoomRecordJpaEntity jpa);
}