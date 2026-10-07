package com.example.ypa_roll_call.connection_token.service;

import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenResponse;
import com.example.ypa_roll_call.common.data.RoomOptions;

import java.io.IOException;

public interface ConnectionTokenService {
    ConnectionTokenResponse getAdminConnectionToken(RoomOptions options) throws IOException;
    ConnectionTokenResponse getParticipantConnectionToken(String roomId);
}
