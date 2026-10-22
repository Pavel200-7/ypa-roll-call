package com.example.ypa_roll_call.room.domain.model.entity.record;

import com.example.ypa_roll_call.room.domain.exception.InvalidRoomOrganizerException;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;

import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class RoomRecord {

    @Getter
    private RoomId id;

    @Getter
    private OrganizerId organizerId;

    private Set<ParticipantRecord> participantRecords;

    private RoomRecord(RoomId id, OrganizerId organizerId) {
        this.id = id;
        this.organizerId = organizerId;
        this.participantRecords = new HashSet<>();
    }

    private RoomRecord(RoomId id, OrganizerId organizerId, Set<ParticipantRecord> participantRecords) {
        this.id = id;
        this.organizerId = organizerId;
        this.participantRecords = new HashSet<>(participantRecords);
    }

    public static RoomRecord createNew(RoomId id, OrganizerId organizerId) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(organizerId);
        return new RoomRecord(id, organizerId);
    }

    public static RoomRecord restore(
            RoomId id,
            OrganizerId organizerId,
            Set<ParticipantRecord> participantRecords) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(organizerId);
        Objects.requireNonNull(participantRecords);
        return new RoomRecord(id, organizerId, participantRecords);
    }

    public boolean isEmpty() {
        return participantRecords.isEmpty();
    }

    public void addParticipant(ParticipantId participantId) {
        ParticipantRecord participantRecord = ParticipantRecord.createNew(participantId);
        participantRecords.add(participantRecord);
    }

    public Set<ParticipantRecord> getParticipantRecords() {
        return Set.copyOf(participantRecords);
    }

    public Optional<ParticipantRecord> getParticipantRecord(ParticipantId participantId) {
        Objects.requireNonNull(participantId);
        return participantRecords.stream()
                .filter(p -> p.getId().equals(participantId))
                .findFirst();
    }

    public boolean isParticipant(ParticipantRecord participant) {
        return participantRecords.contains(participant);
    }

    public boolean isOrganizer(OrganizerId organizerId) {
        return this.organizerId
                .equals(organizerId);
    }

    public void ensureOrganizer(OrganizerId candidate) {
        if (!this.organizerId.equals(candidate)) {
            throw new InvalidRoomOrganizerException(this.id, candidate);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomRecord that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
