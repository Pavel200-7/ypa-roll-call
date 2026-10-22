package com.example.ypa_roll_call.room.domain.model.vo;

import com.example.ypa_roll_call.room.domain.model.enums.RestrictionType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Restrictions(
        Set<RestrictionType> restrictions
) {

    public Restrictions {
        Objects.requireNonNull(restrictions, "restrictions must not be null");
        restrictions = Set.copyOf(restrictions);
    }


    public static Restrictions empty() {
        return new Restrictions(Set.of());
    }

    public static Restrictions of(Set<RestrictionType> restrictions) {
        return new Restrictions(restrictions);
    }


    public Restrictions add(RestrictionType restriction) {
        Set<RestrictionType> copy = new HashSet<>(restrictions);
        copy.add(restriction);
        return new Restrictions(copy);
    }

    public Restrictions remove(RestrictionType restriction) {
        Set<RestrictionType> copy = new HashSet<>(restrictions);
        copy.remove(restriction);
        return new Restrictions(copy);
    }

    public boolean includes(RestrictionType restriction) {
        return restrictions.contains(restriction);
    }
}
