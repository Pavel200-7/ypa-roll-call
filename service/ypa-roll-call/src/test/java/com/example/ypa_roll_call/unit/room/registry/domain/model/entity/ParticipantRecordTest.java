package com.example.ypa_roll_call.unit.room.registry.domain.model.entity;

import com.example.ypa_roll_call.room.domain.model.entity.record.ParticipantRecord;
import com.example.ypa_roll_call.room.domain.model.enums.RestrictionType;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.Restrictions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantRecordTest {

    private final ParticipantId participantId = new ParticipantId(UUID.randomUUID());

    @Test
    void createNew_shouldCreateParticipant_whenIdIsNotNull() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        assertNotNull(participant);
        assertEquals(participantId, participant.getId());
        assertFalse(participant.hasRestriction(RestrictionType.MUTE_MICROPHONE));
        assertFalse(participant.hasRestriction(RestrictionType.BAN));
    }

    @Test
    void createNew_shouldThrow_whenIdIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> ParticipantRecord.createNew(null)
        );
    }

    @Test
    void restore_shouldRestoreParticipant_whenArgumentsAreValid() {
        Restrictions restrictions = Restrictions.empty().add(RestrictionType.BAN);

        ParticipantRecord participant = ParticipantRecord.restore(participantId, restrictions);

        assertEquals(participantId, participant.getId());
        assertTrue(participant.hasRestriction(RestrictionType.BAN));
        assertFalse(participant.hasRestriction(RestrictionType.MUTE_MICROPHONE));
    }

    @Test
    void restore_shouldThrow_whenRestrictionsIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> ParticipantRecord.restore(participantId, null)
        );
    }

    @Test
    void hasRestriction_shouldReturnTrue_whenRestrictionPresent() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);
        participant.ban();

        assertTrue(participant.hasRestriction(RestrictionType.BAN));
    }

    @Test
    void hasRestriction_shouldReturnFalse_whenRestrictionAbsent() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        assertFalse(participant.hasRestriction(RestrictionType.BAN));
    }

    @Test
    void mute_shouldAddMuteRestriction() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        participant.mute();

        assertTrue(participant.hasRestriction(RestrictionType.MUTE_MICROPHONE));
    }

    @Test
    void mute_shouldBeIdempotent_whenCalledTwice() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        participant.mute();
        participant.mute();

        assertTrue(participant.hasRestriction(RestrictionType.MUTE_MICROPHONE));
    }

    @Test
    void unmute_shouldRemoveMuteRestriction() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);
        participant.mute();

        participant.unmute();

        assertFalse(participant.hasRestriction(RestrictionType.MUTE_MICROPHONE));
    }

    @Test
    void unmute_shouldDoNothing_whenNotMuted() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        participant.unmute();

        assertFalse(participant.hasRestriction(RestrictionType.MUTE_MICROPHONE));
    }

    @Test
    void ban_shouldAddBanRestriction() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        participant.ban();

        assertTrue(participant.hasRestriction(RestrictionType.BAN));
    }

    @Test
    void ban_shouldBeIdempotent_whenCalledTwice() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        participant.ban();
        participant.ban();

        assertTrue(participant.hasRestriction(RestrictionType.BAN));
    }

    @Test
    void unban_shouldRemoveBanRestriction() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);
        participant.ban();

        participant.unban();

        assertFalse(participant.hasRestriction(RestrictionType.BAN));
    }

    @Test
    void unban_shouldDoNothing_whenNotBanned() {
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);

        participant.unban();

        assertFalse(participant.hasRestriction(RestrictionType.BAN));
    }

    @Test
    void equals_shouldReturnTrue_whenIdsAreEqual() {
        ParticipantRecord first = ParticipantRecord.createNew(participantId);
        ParticipantRecord second = ParticipantRecord.restore(
                participantId,
                Restrictions.empty().add(RestrictionType.BAN)
        );

        assertEquals(first, second);
    }

    @Test
    void equals_shouldReturnFalse_whenIdsDiffer() {
        ParticipantRecord first = ParticipantRecord.createNew(participantId);
        ParticipantRecord second = ParticipantRecord.createNew(
                new ParticipantId(UUID.randomUUID())
        );

        assertNotEquals(first, second);
    }

    @Test
    void hashCode_shouldBeEqual_whenIdsAreEqual() {
        ParticipantRecord first = ParticipantRecord.createNew(participantId);
        ParticipantRecord second = ParticipantRecord.restore(
                participantId,
                Restrictions.empty().add(RestrictionType.BAN)
        );

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void hashCode_shouldDiffer_whenIdsDiffer() {
        ParticipantRecord first = ParticipantRecord.createNew(participantId);
        ParticipantRecord second = ParticipantRecord.createNew(
                new ParticipantId(UUID.randomUUID())
        );

        assertNotEquals(first.hashCode(), second.hashCode());
    }
}