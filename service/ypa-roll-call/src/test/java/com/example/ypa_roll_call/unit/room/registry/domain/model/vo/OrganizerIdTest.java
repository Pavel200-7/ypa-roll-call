package com.example.ypa_roll_call.unit.room.registry.domain.model.vo;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import com.example.ypa_roll_call.room.domain.model.vo.OrganizerId;

import static org.junit.jupiter.api.Assertions.*;

class OrganizerIdTest {

    @Test
    void constructor_shouldCreate_whenIdIsNotNull() {
        UUID uuid = UUID.randomUUID();

        OrganizerId organizerId = OrganizerId.of(uuid);

        assertEquals(uuid, organizerId.id());
    }

    @Test
    void constructor_shouldThrow_whenIdIsNull() {
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> new OrganizerId(null)
        );

        assertTrue(ex.getMessage().contains("Organizer id must not be null"));
    }
}