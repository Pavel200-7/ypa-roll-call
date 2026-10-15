package com.example.ypa_roll_call.room.data.response;

import lombok.*;

import java.util.UUID;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RoomDto {
    private final UUID roomId;
    private final UUID adminId;
}
