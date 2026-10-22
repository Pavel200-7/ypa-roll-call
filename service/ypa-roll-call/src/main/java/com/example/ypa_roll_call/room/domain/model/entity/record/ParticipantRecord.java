package com.example.ypa_roll_call.room.domain.model.entity.record;

import com.example.ypa_roll_call.room.domain.model.enums.RestrictionType;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.Restrictions;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ParticipantRecord {
    ParticipantId id;
    Restrictions restrictions;

    private ParticipantRecord(ParticipantId id) {
        this.id = id;
        this.restrictions = Restrictions.empty();
    }

    private ParticipantRecord(ParticipantId id, Restrictions restrictions) {
        this.id = id;
        this.restrictions = restrictions;
    }

    public static ParticipantRecord createNew(ParticipantId id) {
        Objects.requireNonNull(id);
        return new ParticipantRecord(id);
    }

    public static ParticipantRecord restore(ParticipantId id, Restrictions restrictions) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(restrictions);
        return new ParticipantRecord(id, restrictions);
    }

    public boolean hasRestriction(RestrictionType restriction) {
        return restrictions.includes(restriction);
    }

    public void mute() {
        this.restrictions = restrictions.add(RestrictionType.MUTE_MICROPHONE);
    }

    public void unmute() {
        this.restrictions = restrictions.remove(RestrictionType.MUTE_MICROPHONE);
    }

    public void ban() {
        this.restrictions = restrictions.add(RestrictionType.BAN);
    }

    public void unban() {
        this.restrictions = restrictions.remove(RestrictionType.BAN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipantRecord that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
