package com.example.ypa_roll_call.room_operations.domain.model.participant.entity;

import com.example.ypa_roll_call.room_operations.domain.model.participant.vo.ParticipantId;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Participant {
    private final ParticipantId id;

    private Participant(UUID id) {
        this.id = new ParticipantId(id);
    }

    public static Participant create(UUID id) {
        return new Participant(id);
    }
}
