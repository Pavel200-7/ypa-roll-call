package com.example.ypa_roll_call.room_restriction.domain;

import com.example.ypa_roll_call.common.entity.base.BaseEntity;
import com.example.ypa_roll_call.room.entity.Room;
import com.example.ypa_roll_call.room_restriction.enums.RestrictionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Table(name = "participant_restrictions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantRestriction extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "participant_id", nullable = false, updatable = false)
    private UUID participantId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "participant_restriction_types",
            joinColumns = @JoinColumn(name = "restriction_id")
    )
    @Column(name = "restriction_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Set<RestrictionType> restrictions = EnumSet.noneOf(RestrictionType.class);

    private ParticipantRestriction(
            Room room,
            UUID participantId) {
        this.id = UUID.randomUUID();
        this.room = room;
        this.participantId = participantId;
    }

    public static ParticipantRestriction createRestriction(
            Room room,
            UUID participantId) {
        return new ParticipantRestriction(room, participantId);
    }

    public void addRestriction(RestrictionType type) {
        this.restrictions.add(type);
    }

    public void removeRestriction(RestrictionType type) {
        this.restrictions.remove(type);
    }

    public boolean hasRestriction(RestrictionType type) {
        return this.restrictions.contains(type);
    }

    public boolean isEmpty() {
        return this.restrictions.isEmpty();
    }
}