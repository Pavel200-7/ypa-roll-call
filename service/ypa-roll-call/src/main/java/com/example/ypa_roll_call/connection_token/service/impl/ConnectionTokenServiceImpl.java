package com.example.ypa_roll_call.connection_token.service.impl;

import com.example.ypa_roll_call.auth.utils.CurrentUser;
import com.example.ypa_roll_call.common.exception.room.RoomNotFoundException;
import com.example.ypa_roll_call.common.logging.annotation.LogOperation;
import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenResponse;
import com.example.ypa_roll_call.connection_token.service.ConnectionTokenService;
import com.example.ypa_roll_call.connection_token.util.AccessTokenFactory;
import com.example.ypa_roll_call.room.data.common.RoomOptions;
import com.example.ypa_roll_call.room.data.request.CreateRoomDto;
import com.example.ypa_roll_call.room.data.response.RoomDto;
import com.example.ypa_roll_call.room.service.RoomService;
import io.livekit.server.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionTokenServiceImpl implements ConnectionTokenService {
    private final CurrentUser currentUser;
    private final RoomService roomService;
    private final AccessTokenFactory tokenFactory;

    @Override
    @LogOperation("ISSUE_ADMIN_TOKEN")
    public ConnectionTokenResponse getAdminConnectionToken(RoomOptions options) {
        CreateRoomDto createDto = new CreateRoomDto(currentUser.getUserId(), options);
        RoomDto room = roomService.createRoom(createDto);

        String jwt = tokenFactory.createAdminToken(
                room.getRoomId(),
                currentUser.getUserIdAsString()
        );

        return new ConnectionTokenResponse(jwt);
    }

    @Override
    @LogOperation("ISSUE_CONNECTION_TOKEN")
    public ConnectionTokenResponse getParticipantConnectionToken(UUID roomId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        boolean isAdmin = roomService.getRoomAdmin(roomId)
                .equals(currentUser.getUserId());

        String identity = currentUser.getUserIdAsString();
        String jwt = isAdmin
                ? tokenFactory.createAdminToken(roomId, identity)
                : tokenFactory.createParticipantToken(roomId, identity);

        return new ConnectionTokenResponse(jwt);
    }
}
