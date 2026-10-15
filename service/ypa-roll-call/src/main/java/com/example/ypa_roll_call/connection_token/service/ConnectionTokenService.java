package com.example.ypa_roll_call.connection_token.service;

import com.example.ypa_roll_call.common.exception.room.ParticipantBlockedException;
import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenDto;
import com.example.ypa_roll_call.room.data.common.RoomOptions;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

public interface ConnectionTokenService {
    ConnectionTokenDto getAdminConnectionToken(RoomOptions options);
    ConnectionTokenDto getParticipantConnectionToken(UUID roomId) throws ParticipantBlockedException;
}
