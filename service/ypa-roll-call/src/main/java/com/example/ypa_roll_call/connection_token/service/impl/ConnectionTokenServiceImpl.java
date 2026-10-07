package com.example.ypa_roll_call.connection_token.service.impl;

import com.example.ypa_roll_call.auth.utils.CurrentUser;
import com.example.ypa_roll_call.common.conf.LiveKitProperties;
import com.example.ypa_roll_call.common.enums.ParticipantRole;
import com.example.ypa_roll_call.common.util.ParticipantMetadataEncoder;
import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenResponse;
import com.example.ypa_roll_call.connection_token.service.ConnectionTokenService;
import com.example.ypa_roll_call.common.data.RoomOptions;
import com.example.ypa_roll_call.room.service.RoomService;
import io.livekit.server.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionTokenServiceImpl implements ConnectionTokenService {
    private final LiveKitProperties properties;
    private final CurrentUser currentUser;
    private final RoomService roomService;
    private final ParticipantMetadataEncoder metaDataEncoder;

    @Override
    public ConnectionTokenResponse getAdminConnectionToken(RoomOptions options) throws IOException {
        String newRoomId = UUID.randomUUID().toString();

        roomService.createRoom(newRoomId, options);

        AccessToken token = getAccessTokenToken(newRoomId);
        token.addGrants(new RoomAdmin(true));
        token.setMetadata(metaDataEncoder.encode(ParticipantRole.ADMIN));
        return new ConnectionTokenResponse(token.toJwt());
    }

    @Override
    public ConnectionTokenResponse getParticipantConnectionToken(String roomId) {
        AccessToken token = getAccessTokenToken(roomId);
        token.addGrants(new CanUpdateOwnMetadata(false));
        return new ConnectionTokenResponse(token.toJwt());
    }


    private AccessToken getAccessTokenToken(String roomId) {
        AccessToken token = new AccessToken(properties.getApiKey(), properties.getApiSecret());
        token.setIdentity(currentUser.getUserId());
        token.addGrants(
                new RoomName(roomId),
                new RoomJoin(true),
                new CanSubscribe(true),
                new CanPublishData(true),
                new CanPublish(true),
                new CanPublishSources(List.of("camera", "microphone", "screen_share", "screen_share_audio"))
        );
        return token;
    }
}
