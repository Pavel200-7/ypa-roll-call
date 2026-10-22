package com.example.ypa_roll_call.room.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRecordJpaRepository extends JpaRepository<RoomRecordJpaEntity, UUID> {
}