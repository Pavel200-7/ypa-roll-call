package com.example.ypa_roll_call.room.data.request;

import com.example.ypa_roll_call.room.data.common.RoomOptions;
import lombok.*;

import java.util.UUID;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateRoomDto {
    private UUID adminId;
    private RoomOptions options;
}
