package com.example.ypa_roll_call.room.data.common;

import com.example.ypa_roll_call.room.enums.RestrictionType;
import lombok.*;

import java.util.UUID;

@Data
public class RestrictionDto {
    private final UUID roomId;
    private final UUID participantId;
    private final RestrictionType type;
}
