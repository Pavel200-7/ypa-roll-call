package com.example.ypa_roll_call.room.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "participant_record",
        uniqueConstraints = @UniqueConstraint(columnNames = {"participant_id", "room_id"}))
@Getter
@Setter
@NoArgsConstructor
public class RoomRecordJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "organizer_id", nullable = false)
    private UUID organizerId;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ParticipantRecordJpaEntity> participants = new HashSet<>();

    public void addParticipant(ParticipantRecordJpaEntity participant) {
        participants.add(participant);
        participant.setRoom(this);
    }
}