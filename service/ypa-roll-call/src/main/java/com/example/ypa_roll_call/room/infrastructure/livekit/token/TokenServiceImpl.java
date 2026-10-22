package com.example.ypa_roll_call.room.infrastructure.livekit.token;

import com.example.ypa_roll_call.common.conf.LiveKitProperties;
import com.example.ypa_roll_call.common.annotation.logging.annotation.LogOperation;
import com.example.ypa_roll_call.room.infrastructure.livekit.token.enums.ParticipantRole;
import com.example.ypa_roll_call.room.infrastructure.livekit.token.util.ParticipantMetadataEncoder;
import com.example.ypa_roll_call.room.application.port.out.TokenService;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.Restrictions;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import com.example.ypa_roll_call.room.domain.model.vo.Token;
import com.example.ypa_roll_call.room.domain.model.enums.RestrictionType;

import io.livekit.server.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final LiveKitProperties properties;
    private final ParticipantMetadataEncoder metaDataEncoder;

    private static final List<String> ALL_PUBLISH_SOURCES = List.of(
            "camera", "microphone", "screen_share", "screen_share_audio"
    );
    
    @Override
    @LogOperation("ISSUE_ADMIN_TOKEN")
    public Token getAdminToken(RoomId roomId, ParticipantId participantId) {
        AccessToken token = createBaseToken(roomId, participantId, ALL_PUBLISH_SOURCES);
        token.addGrants(new RoomAdmin(true));
        token.setMetadata(metaDataEncoder.encode(ParticipantRole.ORGANIZER));
        return Token.of(token.toJwt());
    }

    @Override
    @LogOperation("ISSUE_PARTICIPANT_TOKEN")
    public Token getParticipantToken(RoomId roomId, ParticipantId participantId, Restrictions restrictions) {
        List<String> availableSources = applyRestrictions(restrictions);

        AccessToken token = createBaseToken(roomId, participantId, availableSources);
        token.addGrants(new CanUpdateOwnMetadata(false));
        token.setMetadata(metaDataEncoder.encode(ParticipantRole.PARTICIPANT));
        
        return Token.of(token.toJwt());
    }

    private AccessToken createBaseToken(
            RoomId roomId, 
            ParticipantId participantId,
            List<String> availableSources
    ) {
        AccessToken token = new AccessToken(
                properties.getApiKey(),
                properties.getApiSecret()
        );
        token.setIdentity(participantId.id().toString());
        token.addGrants(
                new RoomName(roomId.id().toString()),
                new RoomJoin(true),
                new CanSubscribe(true),
                new CanPublishData(true)
        );

        boolean canPublish = !availableSources.isEmpty();
        if (canPublish) {
            token.addGrants(new CanPublish(true));
            if (!availableSources.isEmpty()) {
                token.addGrants(new CanPublishSources(availableSources));
            }
        }

        return token;
    }

    private List<String> applyRestrictions(Restrictions restrictions) {
        List<String> sources = new ArrayList<>(ALL_PUBLISH_SOURCES);

        if (restrictions.includes(RestrictionType.MUTE_MICROPHONE)) {
            sources.remove("microphone");
        }
        if (restrictions.includes(RestrictionType.MUTE_CAMERA)) {
            sources.remove("camera");
        }
        if (restrictions.includes(RestrictionType.MUTE_SCREEN_SHARE)) {
            sources.remove("screen_share");
            sources.remove("screen_share_audio");
        }

        return sources;
    }
}
