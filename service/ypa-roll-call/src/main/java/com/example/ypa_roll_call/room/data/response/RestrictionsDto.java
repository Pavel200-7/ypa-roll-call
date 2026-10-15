package com.example.ypa_roll_call.room.data.response;

import com.example.ypa_roll_call.room.enums.RestrictionType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RestrictionsDto {
    private final UUID roomId;
    private final UUID participantId;
    private final List<RestrictionType> types;
}
