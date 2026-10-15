package com.example.ypa_roll_call.room.data.request;

import com.example.ypa_roll_call.room.data.common.RoomOptions;
import lombok.*;

import java.util.UUID;

@Data
public class CreateRoomDto {
    private final UUID adminId;
    private final RoomOptions options;
}
