package com.example.ypa_roll_call.room_restriction.data.common;

import com.example.ypa_roll_call.room_restriction.enums.RestrictionType;
import lombok.*;

import java.util.UUID;

@Data
public class RestrictionDto {
    private final UUID roomId;
    private final UUID participantId;
    private final RestrictionType type;
}
