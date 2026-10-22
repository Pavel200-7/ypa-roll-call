package com.example.ypa_roll_call.unit.room.registry.domain.model.vo;

import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoomIdTest {

    @Test
    void constructor_shouldCreate_whenIdIsNotNull() {
        UUID uuid = UUID.randomUUID();

        RoomId roomId = new RoomId(uuid);

        assertEquals(uuid, roomId.id());
    }

    @Test
    void constructor_shouldThrow_whenIdIsNull() {
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> new RoomId(null)
        );

        assertTrue(ex.getMessage().contains("Room id must not be null"));
    }
}