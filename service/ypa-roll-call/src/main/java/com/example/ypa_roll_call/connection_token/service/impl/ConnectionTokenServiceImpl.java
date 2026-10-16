package com.example.ypa_roll_call.connection_token.service.impl;

import com.example.ypa_roll_call.auth.utils.CurrentUser;
import com.example.ypa_roll_call.common.exception.room.ParticipantBlockedException;
import com.example.ypa_roll_call.common.exception.room.RoomNotFoundException;
import com.example.ypa_roll_call.common.logging.annotation.LogOperation;
import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenDto;
import com.example.ypa_roll_call.connection_token.service.ConnectionTokenService;
import com.example.ypa_roll_call.connection_token.util.AccessTokenFactory;
import com.example.ypa_roll_call.room.data.common.RoomOptions;
import com.example.ypa_roll_call.room.data.request.CreateRoomDto;
import com.example.ypa_roll_call.room_restriction.data.request.GetRestrictionsDto;
import com.example.ypa_roll_call.room_restriction.data.common.RestrictionsDto;
import com.example.ypa_roll_call.room.data.response.RoomDto;
import com.example.ypa_roll_call.room_restriction.enums.RestrictionType;
import com.example.ypa_roll_call.room_restriction.service.RestrictionService;
import com.example.ypa_roll_call.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionTokenServiceImpl implements ConnectionTokenService {
    private final CurrentUser currentUser;
    private final RoomService roomService;
    private final AccessTokenFactory tokenFactory;
    private final RestrictionService restrictionService;


    @Override
    @LogOperation("ISSUE_ADMIN_TOKEN")
    public ConnectionTokenDto getAdminConnectionToken(RoomOptions options) {
        CreateRoomDto createDto = new CreateRoomDto(currentUser.getUserId(), options);
        RoomDto room = roomService.createRoom(createDto);

        String jwt = tokenFactory.createAdminToken(
                room.getRoomId(),
                currentUser.getUserIdAsString()
        );

        return new ConnectionTokenDto(jwt);
    }

    @Override
    @LogOperation("ISSUE_CONNECTION_TOKEN")
    public ConnectionTokenDto getParticipantConnectionToken(UUID roomId) {
        if (!roomService.roomExists(roomId)) {
            throw new RoomNotFoundException(roomId);
        }

        UUID userId = currentUser.getUserId();
        RestrictionsDto restrictionsDto = restrictionService.getRestrictions(
                new GetRestrictionsDto(roomId, userId)
        );
        List<RestrictionType> restrictions = restrictionsDto.getTypes();

        if (restrictions.contains(RestrictionType.BLOCK)) {
            log.warn("Blocked user {} attempted to join room {}", userId, roomId);
            throw new ParticipantBlockedException(roomId);
        }

        boolean isAdmin = roomService.getRoomAdmin(roomId)
                .equals(currentUser.getUserId());

        String jwt = isAdmin
                ? tokenFactory.createAdminToken(roomId, userId.toString())
                : tokenFactory.createParticipantToken(roomId, userId.toString(), restrictions);

        return new ConnectionTokenDto(jwt);
    }
}
