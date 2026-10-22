package com.example.ypa_roll_call.room.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.ypa_roll_call.room.domain.model.enums.RestrictionType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "participant_record")
@Getter
@Setter
@NoArgsConstructor
public class ParticipantRecordJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "participant_id", nullable = false, updatable = false)
    private UUID participantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomRecordJpaEntity room;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "participant_restrictions",
            joinColumns = @JoinColumn(name = "participant_record_id"))
    @Column(name = "restriction_type")
    @Enumerated(EnumType.STRING)
    private Set<RestrictionType> restrictions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipantRecordJpaEntity that)) return false;
        return Objects.equals(participantId, that.participantId)
                && Objects.equals(room != null ? room.getId() : null,
                that.room != null ? that.room.getId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, room != null ? room.getId() : null);
    }
}