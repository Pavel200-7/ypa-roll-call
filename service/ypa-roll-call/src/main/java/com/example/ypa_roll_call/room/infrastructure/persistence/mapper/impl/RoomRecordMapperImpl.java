package com.example.ypa_roll_call.room.infrastructure.persistence.mapper.impl;

import com.example.ypa_roll_call.room.domain.model.entity.record.ParticipantRecord;
import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import com.example.ypa_roll_call.room.infrastructure.persistence.jpa.ParticipantRecordJpaEntity;
import com.example.ypa_roll_call.room.infrastructure.persistence.jpa.RoomRecordJpaEntity;
import com.example.ypa_roll_call.room.infrastructure.persistence.mapper.ParticipantRecordMapper;
import com.example.ypa_roll_call.room.infrastructure.persistence.mapper.RoomRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomRecordMapperImpl implements RoomRecordMapper {

    private final ParticipantRecordMapper participantMapper;

    public RoomRecordJpaEntity toJpa(RoomRecord domain) {
        if (domain == null) {
            return null;
        }
        RoomRecordJpaEntity entity = new RoomRecordJpaEntity();
        entity.setId(domain.getId().id());
        entity.setOrganizerId(domain.getOrganizerId().id());

        for (ParticipantRecord participant : domain.getParticipantRecords()) {
            ParticipantRecordJpaEntity pe = participantMapper.toJpa(participant);
            entity.addParticipant(pe);
        }
        return entity;
    }

    public void updateJpa(RoomRecord domain, RoomRecordJpaEntity target) {
        if (domain == null || target == null) {
            return;
        }
        target.setOrganizerId(domain.getOrganizerId().id());

        Map<UUIDKey, ParticipantRecordJpaEntity> existing = target.getParticipants().stream()
                .collect(Collectors.toMap(
                        p -> new UUIDKey(p.getParticipantId()),
                        Function.identity()
                ));

        Set<UUIDKey> domainKeys = new HashSet<>();

        for (ParticipantRecord participant : domain.getParticipantRecords()) {
            UUIDKey key = new UUIDKey(participant.getId().id());
            domainKeys.add(key);

            ParticipantRecordJpaEntity jpa = existing.get(key);
            if (jpa == null) {
                // новый участник
                ParticipantRecordJpaEntity pe = participantMapper.toJpa(participant);
                target.addParticipant(pe);
            } else {
                // существующий — обновляем restrictions
                participantMapper.updateJpa(participant, jpa);
            }
        }

        // удаляем участников, которых больше нет в домене
        Iterator<ParticipantRecordJpaEntity> it = target.getParticipants().iterator();
        while (it.hasNext()) {
            ParticipantRecordJpaEntity p = it.next();
            if (!domainKeys.contains(new UUIDKey(p.getParticipantId()))) {
                it.remove(); // orphanRemoval = true удалит запись из БД
            }
        }
    }

    public RoomRecord toDomain(RoomRecordJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        RoomId id = new RoomId(jpa.getId());
        OrganizerId organizerId = OrganizerId.of(jpa.getOrganizerId());

        Set<ParticipantRecord> participants = jpa.getParticipants() == null
                ? Set.of()
                : jpa.getParticipants().stream()
                .map(participantMapper::toDomain)
                .collect(Collectors.toCollection(HashSet::new));

        return RoomRecord.restore(id, organizerId, participants);
    }

    /** Обёртка над UUID, чтобы использовать как ключ Map без коллизий с другими UUID. */
    private record UUIDKey(java.util.UUID value) {
    }
}