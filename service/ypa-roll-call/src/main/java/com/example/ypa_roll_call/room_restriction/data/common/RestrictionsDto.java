package com.example.ypa_roll_call.room_restriction.data.common;

import com.example.ypa_roll_call.room_restriction.enums.RestrictionType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RestrictionsDto {
    private final UUID roomId;
    private final UUID participantId;
    private final List<RestrictionType> types;
}
