package com.example.ypa_roll_call.connection_token.util;

import com.example.ypa_roll_call.common.conf.LiveKitProperties;
import com.example.ypa_roll_call.common.enums.ParticipantRole;
import com.example.ypa_roll_call.common.util.ParticipantMetadataEncoder;
import io.livekit.server.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccessTokenFactory {

    private static final List<String> PUBLISH_SOURCES = List.of(
            "camera", "microphone", "screen_share", "screen_share_audio"
    );

    private final LiveKitProperties properties;
    private final ParticipantMetadataEncoder metaDataEncoder;

    /**
     * Токен администратора комнаты.
     */
    public String createAdminToken(UUID roomId, String identity) {
        AccessToken token = createBaseToken(roomId, identity);
        token.addGrants(new RoomAdmin(true));
        token.setMetadata(metaDataEncoder.encode(ParticipantRole.ADMIN));
        return token.toJwt();
    }

    /**
     * Токен участника комнаты.
     */
    public String createParticipantToken(UUID roomId, String identity) {
        AccessToken token = createBaseToken(roomId, identity);
        token.addGrants(new CanUpdateOwnMetadata(false));
        token.setMetadata(metaDataEncoder.encode(ParticipantRole.PARTICIPANT));
        return token.toJwt();
    }

    /**
     * Базовый токен для подключения к комнате.
     * Без admin-прав, без специфичной роли.
     */
    private AccessToken createBaseToken(UUID roomId, String identity) {
        AccessToken token = new AccessToken(
                properties.getApiKey(),
                properties.getApiSecret()
        );
        token.setIdentity(identity);
        token.addGrants(
                new RoomName(roomId.toString()),
                new RoomJoin(true),
                new CanSubscribe(true),
                new CanPublishData(true),
                new CanPublish(true),
                new CanPublishSources(PUBLISH_SOURCES)
        );
        return token;
    }
}