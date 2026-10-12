package com.example.ypa_roll_call.connection_token.service;

import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenResponse;
import com.example.ypa_roll_call.common.data.RoomOptions;

import java.io.IOException;
import java.util.UUID;

public interface ConnectionTokenService {
    ConnectionTokenResponse getAdminConnectionToken(RoomOptions options) throws IOException;
    ConnectionTokenResponse getAdminConnectionTokenForRoom(String roomId) throws IOException;
    ConnectionTokenResponse getParticipantConnectionToken(String roomId) throws IOException;
}
