package com.example.ypa_roll_call.room.infrastructure.persistence.mapper.impl;

import com.example.ypa_roll_call.room.domain.model.entity.record.ParticipantRecord;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.Restrictions;
import com.example.ypa_roll_call.room.infrastructure.persistence.jpa.ParticipantRecordJpaEntity;
import com.example.ypa_roll_call.room.infrastructure.persistence.mapper.ParticipantRecordMapper;

import java.util.HashSet;

public class ParticipantRecordMapperImpl implements ParticipantRecordMapper {

    public ParticipantRecordJpaEntity toJpa(ParticipantRecord domain) {
        if (domain == null) {
            return null;
        }
        ParticipantRecordJpaEntity entity = new ParticipantRecordJpaEntity();
        entity.setParticipantId(domain.getId().id());
        entity.setRestrictions(new HashSet<>(domain.getRestrictions().restrictions()));
        return entity;
    }

    public void updateJpa(ParticipantRecord domain, ParticipantRecordJpaEntity target) {
        if (domain == null || target == null) {
            return;
        }
        target.setParticipantId(domain.getId().id());
        target.getRestrictions().clear();
        target.getRestrictions().addAll(domain.getRestrictions().restrictions());
    }

    public ParticipantRecord toDomain(ParticipantRecordJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        ParticipantId participantId = new ParticipantId(jpa.getParticipantId());
        Restrictions restrictions = jpa.getRestrictions() == null || jpa.getRestrictions().isEmpty()
                ? Restrictions.empty()
                : Restrictions.of(new HashSet<>(jpa.getRestrictions()));
        return ParticipantRecord.restore(participantId, restrictions);
    }
}