package com.example.ypa_roll_call.room.application.port.out;

import com.example.ypa_roll_call.room.domain.model.vo.UserId;

import java.util.UUID;

public interface CurrentUser {
    UserId getUserId();
}
