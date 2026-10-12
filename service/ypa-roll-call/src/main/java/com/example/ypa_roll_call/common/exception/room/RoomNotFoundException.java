package com.example.ypa_roll_call.common.exception.room;

import com.example.ypa_roll_call.common.exception.base.ResourceNotFoundException;

public class RoomNotFoundException extends ResourceNotFoundException {
    public RoomNotFoundException(String roomId) {
        super("Room not found: " + roomId);
    }
}
