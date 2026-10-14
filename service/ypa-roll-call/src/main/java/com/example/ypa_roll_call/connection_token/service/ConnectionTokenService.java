package com.example.ypa_roll_call.connection_token.service;

import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenResponse;
import com.example.ypa_roll_call.room.data.common.RoomOptions;

import java.io.IOException;
import java.util.UUID;

public interface ConnectionTokenService {
    ConnectionTokenResponse getAdminConnectionToken(RoomOptions options);
    ConnectionTokenResponse getParticipantConnectionToken(UUID roomId);
}
