package com.example.ypa_roll_call.room.domain.model.entity.room;


import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
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
