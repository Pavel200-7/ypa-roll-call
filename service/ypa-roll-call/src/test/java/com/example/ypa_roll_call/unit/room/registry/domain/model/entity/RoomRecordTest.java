package com.example.ypa_roll_call.unit.room.registry.domain.model.entity;

import com.example.ypa_roll_call.room.domain.model.entity.record.ParticipantRecord;
import com.example.ypa_roll_call.room.domain.model.entity.record.RoomRecord;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoomRecordTest {

    private final RoomId roomId = new RoomId(UUID.randomUUID());
    private final OrganizerId organizerId = OrganizerId.of(UUID.randomUUID());

    @Test
    void createNew_shouldCreateRoom_whenArgumentsAreValid() {
        RoomRecord room = RoomRecord.createNew(roomId, organizerId);

        assertNotNull(room);
        assertEquals(roomId, room.getId());
        assertEquals(organizerId, room.getOrganizerId());
        assertTrue(room.isEmpty());
    }

    @Test
    void createNew_shouldThrow_whenIdIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> RoomRecord.createNew(null, organizerId)
        );
    }

    @Test
    void restore_shouldRestoreRoom_whenArgumentsAreValid() {
        Set<ParticipantRecord> participants = Set.of(
                ParticipantRecord.createNew(new ParticipantId(UUID.randomUUID()))
        );

        RoomRecord room = RoomRecord.restore(roomId, organizerId, participants);

        assertEquals(roomId, room.getId());
        assertEquals(organizerId, room.getOrganizerId());
        assertFalse(participants.isEmpty());
    }

    @Test
    void restore_shouldThrow_whenParticipantRecordsIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> RoomRecord.restore(roomId, organizerId, null)
        );
    }

    @Test
    void addParticipant_shouldAddParticipant_whenSetIsValid() {

        RoomRecord room = RoomRecord.createNew(roomId, organizerId);
        ParticipantId participantId = new ParticipantId(UUID.randomUUID());

        room.addParticipant(participantId);

        ParticipantRecord expected = ParticipantRecord.createNew(participantId);
        assertTrue(room.isParticipant(expected));
    }

    @Test
    void isParticipant_shouldReturnTrue_whenParticipantPresent() {
        ParticipantRecord participant =
                ParticipantRecord.createNew(new ParticipantId(UUID.randomUUID()));
        RoomRecord room = RoomRecord.restore(roomId, organizerId, Set.of(participant));

        assertTrue(room.isParticipant(participant));
    }

    @Test
    void isParticipant_shouldReturnFalse_whenParticipantAbsent() {
        RoomRecord room = RoomRecord.createNew(roomId, organizerId);
        ParticipantRecord stranger =
                ParticipantRecord.createNew(new ParticipantId(UUID.randomUUID()));

        assertFalse(room.isParticipant(stranger));
    }


    @Test
    void isOrganizer_shouldReturnTrue_whenOrganizerMatches() {
        RoomRecord room = RoomRecord.createNew(roomId, organizerId);

        assertTrue(room.isOrganizer(organizerId));
    }

    @Test
    void isOrganizer_shouldReturnFalse_whenOrganizerDiffers() {
        RoomRecord room = RoomRecord.createNew(roomId, organizerId);
        OrganizerId other = OrganizerId.of(UUID.randomUUID());

        assertFalse(room.isOrganizer(other));
    }


    @Test
    void equals_shouldReturnTrue_whenIdsAreEqual() {
        RoomRecord first = RoomRecord.createNew(roomId, organizerId);
        RoomRecord second = RoomRecord.createNew(roomId, OrganizerId.of(UUID.randomUUID()));

        assertEquals(first, second);
    }

    @Test
    void equals_shouldReturnFalse_whenIdsDiffer() {
        RoomRecord first = RoomRecord.createNew(roomId, organizerId);
        RoomRecord second = RoomRecord.createNew(new RoomId(UUID.randomUUID()), organizerId);

        assertNotEquals(first, second);
    }

    @Test
    void hashCode_shouldBeEqual_whenIdsAreEqual() {
        RoomRecord first = RoomRecord.createNew(roomId, organizerId);
        RoomRecord second = RoomRecord.createNew(roomId, OrganizerId.of(UUID.randomUUID()));

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void hashCode_shouldDiffer_whenIdsDiffer() {
        RoomRecord first = RoomRecord.createNew(roomId, organizerId);
        RoomRecord second = RoomRecord.createNew(new RoomId(UUID.randomUUID()), organizerId);

        assertNotEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void getParticipantRecord_shouldReturnParticipant_whenPresent() {
        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        ParticipantRecord participant = ParticipantRecord.createNew(participantId);
        RoomRecord room = RoomRecord.restore(roomId, organizerId, Set.of(participant));

        Optional<ParticipantRecord> result = room.getParticipantRecord(participantId);

        assertTrue(result.isPresent());
        assertEquals(participant, result.get());
    }

    @Test
    void getParticipantRecord_shouldReturnEmpty_whenAbsent() {
        RoomRecord room = RoomRecord.createNew(roomId, organizerId);
        ParticipantId unknownId = new ParticipantId(UUID.randomUUID());

        Optional<ParticipantRecord> result = room.getParticipantRecord(unknownId);

        assertTrue(result.isEmpty());
    }
}