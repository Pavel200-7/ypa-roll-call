package com.example.ypa_roll_call.room_restriction.data.request;

import lombok.Data;

import java.util.UUID;

@Data
public class GetRestrictionsDto {
    private final UUID roomId;
    private final UUID participantId;
}
