package com.example.ypa_roll_call.room.entity;

import com.example.ypa_roll_call.common.entity.base.BaseEntity;
import com.example.ypa_roll_call.room.enums.RestrictionType;
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

    @Column(name = "room_id", nullable = false, updatable = false)
    private UUID roomId;

    @Column(name = "participant_id", nullable = false, updatable = false)
    private UUID participantId;

    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "participant_restriction_types",
            joinColumns = @JoinColumn(name = "restriction_id")
    )
    @Column(name = "restriction_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Set<RestrictionType> restrictions = EnumSet.noneOf(RestrictionType.class);

    public ParticipantRestriction(
            UUID roomId,
            UUID participantId,
            UUID createdBy
    ) {
        this.id = UUID.randomUUID();
        this.roomId = roomId;
        this.participantId = participantId;
        this.createdBy = createdBy;
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