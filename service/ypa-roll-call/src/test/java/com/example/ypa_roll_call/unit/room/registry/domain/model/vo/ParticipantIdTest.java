package com.example.ypa_roll_call.unit.room.registry.domain.model.vo;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantIdTest {

    @Test
    void constructor_shouldCreate_whenIdIsNotNull() {
        UUID uuid = UUID.randomUUID();

        ParticipantId participantId = new ParticipantId(uuid);

        assertEquals(uuid, participantId.id());
    }

    @Test
    void constructor_shouldThrow_whenIdIsNull() {
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> new ParticipantId(null)
        );

        assertTrue(ex.getMessage().contains("Participant id must not be null"));
    }
}