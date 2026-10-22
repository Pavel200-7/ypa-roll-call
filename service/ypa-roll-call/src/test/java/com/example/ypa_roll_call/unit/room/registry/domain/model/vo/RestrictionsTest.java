package com.example.ypa_roll_call.unit.room.registry.domain.model.vo;

import com.example.ypa_roll_call.room.domain.model.enums.RestrictionType;
import com.example.ypa_roll_call.room.domain.model.vo.Restrictions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RestrictionsTest {

    @Test
    void constructor_shouldCreate_whenSetIsNotNull() {
        Set<RestrictionType> source = Set.of(RestrictionType.MUTE_MICROPHONE);

        Restrictions restrictions = Restrictions.of(source);

        assertEquals(Set.of(RestrictionType.MUTE_MICROPHONE), restrictions.restrictions());
    }

    @Test
    void constructor_shouldThrow_whenSetIsNull() {
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> new Restrictions(null)
        );

        assertTrue(ex.getMessage().contains("restrictions must not be null"));
    }

    @Test
    void constructor_shouldMakeDefensiveCopy_whenSourceIsModified() {
        Set<RestrictionType> source = new HashSet<>();
        source.add(RestrictionType.MUTE_MICROPHONE);

        Restrictions restrictions = Restrictions.of(source);
        source.add(RestrictionType.BAN);

        assertEquals(Set.of(RestrictionType.MUTE_MICROPHONE), restrictions.restrictions());
    }

    @Test
    void empty_shouldReturnEmptyRestrictions() {
        Restrictions restrictions = Restrictions.empty();

        assertTrue(restrictions.restrictions().isEmpty());
    }

    @Test
    void empty_shouldNotContainAnyRestriction() {
        Restrictions restrictions = Restrictions.empty();

        assertFalse(restrictions.includes(RestrictionType.MUTE_MICROPHONE));
    }


    @Test
    void add_shouldAddRestriction() {
        Restrictions restrictions = Restrictions.empty();

        Restrictions result = restrictions.add(RestrictionType.MUTE_MICROPHONE);

        assertEquals(Set.of(RestrictionType.MUTE_MICROPHONE), result.restrictions());
    }

    @Test
    void add_shouldNotModifyOriginal_whenRestrictionAdded() {
        Restrictions original = Restrictions.empty();

        Restrictions result = original.add(RestrictionType.MUTE_MICROPHONE);

        assertTrue(original.restrictions().isEmpty());
        assertNotSame(original, result);
    }


    @Test
    void remove_shouldRemoveRestriction() {
        Restrictions restrictions = Restrictions.empty().add(RestrictionType.MUTE_MICROPHONE);

        Restrictions result = restrictions.remove(RestrictionType.MUTE_MICROPHONE);

        assertTrue(result.restrictions().isEmpty());
    }

    @Test
    void remove_shouldLeaveSetUnchanged_whenRestrictionAbsent() {
        Restrictions restrictions = Restrictions.empty().add(RestrictionType.MUTE_MICROPHONE);

        Restrictions result = restrictions.remove(RestrictionType.BAN);

        assertEquals(Set.of(RestrictionType.MUTE_MICROPHONE), result.restrictions());
    }

    // ---------- includes() ----------

    @Test
    void includes_shouldReturnTrue_whenRestrictionPresent() {
        Restrictions restrictions = Restrictions.empty().add(RestrictionType.MUTE_MICROPHONE);

        assertTrue(restrictions.includes(RestrictionType.MUTE_MICROPHONE));
    }

    @Test
    void includes_shouldReturnFalse_whenRestrictionAbsent() {
        Restrictions restrictions = Restrictions.empty();

        assertFalse(restrictions.includes(RestrictionType.BAN));
    }
}