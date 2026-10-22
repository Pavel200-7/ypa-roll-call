package com.example.ypa_roll_call.room.infrastructure.persistence.mapper;

import com.example.ypa_roll_call.room.domain.model.entity.record.ParticipantRecord;
import com.example.ypa_roll_call.room.infrastructure.persistence.jpa.ParticipantRecordJpaEntity;

public interface ParticipantRecordMapper {
    ParticipantRecordJpaEntity toJpa(ParticipantRecord domain);
    void updateJpa(ParticipantRecord domain, ParticipantRecordJpaEntity target);
    ParticipantRecord toDomain(ParticipantRecordJpaEntity jpa);
}