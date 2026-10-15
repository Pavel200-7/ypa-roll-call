package com.example.ypa_roll_call.room.repository;

import com.example.ypa_roll_call.room.entity.ParticipantRestriction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParticipantRestrictionRepository extends JpaRepository<ParticipantRestriction, UUID> {
    Optional<ParticipantRestriction> findByRoomIdAndParticipantId(
            UUID roomId, UUID participantId);
    boolean existsByRoomIdAndParticipantId(
            UUID roomId, UUID participantId);
    void deleteByRoomId(UUID roomId);
}