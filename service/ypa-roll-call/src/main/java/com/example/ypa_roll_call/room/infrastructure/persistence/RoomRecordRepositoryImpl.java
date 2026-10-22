package com.example.ypa_roll_call.room.infrastructure.persistence;

import com.example.ypa_roll_call.room.application.port.out.RoomRecordRepository;
import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import com.example.ypa_roll_call.room.infrastructure.persistence.jpa.RoomRecordJpaEntity;
import com.example.ypa_roll_call.room.infrastructure.persistence.jpa.RoomRecordJpaRepository;
import com.example.ypa_roll_call.room.infrastructure.persistence.mapper.RoomRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoomRecordRepositoryImpl implements RoomRecordRepository {

    private final RoomRecordJpaRepository jpaRepository;
    private final RoomRecordMapper mapper;

    @Override
    @Transactional
    public RoomRecord save(RoomRecord roomRecord) {
        RoomRecordJpaEntity entity = jpaRepository
                .findById(roomRecord.getId().id())
                .map(existing -> {
                    mapper.updateJpa(roomRecord, existing);
                    return existing;
                })
                .orElseGet(() -> mapper.toJpa(roomRecord));

        RoomRecordJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomRecord> findById(RoomId roomId) {
        return jpaRepository.findById(roomId.id())
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(RoomId roomId) {
        return jpaRepository.existsById(roomId.id());
    }

    @Override
    @Transactional
    public void deleteById(RoomId roomId) {
        jpaRepository.deleteById(roomId.id());
    }
}