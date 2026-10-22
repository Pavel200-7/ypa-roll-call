//package com.example.ypa_roll_call.connection_token.util;
//
//import com.example.ypa_roll_call.common.conf.LiveKitProperties;
//import com.example.ypa_roll_call.common.enums.ParticipantRole;
//import util.value.livekit.infrastructure.com.example.ypa_roll_call.room.ParticipantMetadataEncoder;
//import com.example.ypa_roll_call.room_restriction.enums.RestrictionType;
//import io.livekit.server.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class AccessTokenFactory {
//
//    private static final List<String> ALL_PUBLISH_SOURCES = List.of(
//            "camera", "microphone", "screen_share", "screen_share_audio"
//    );
//
//    private final LiveKitProperties properties;
//    private final ParticipantMetadataEncoder metaDataEncoder;
//
//    public String createAdminToken(UUID roomId, String identity) {
//        AccessToken value = createBaseToken(roomId, identity, ALL_PUBLISH_SOURCES, true);
//        value.addGrants(new RoomAdmin(true));
//        value.setMetadata(metaDataEncoder.encode(ParticipantRole.ADMIN));
//        return value.toJwt();
//    }
//
//    public String createParticipantToken(
//            UUID roomId,
//            String identity,
//            List<RestrictionType> restrictions
//    ) {
//        List<String> sources = applyRestrictions(restrictions);
//
//        // Если после применения ограничений источников не осталось —
//        // canPublish вообще не выдаём
//        boolean canPublish = !sources.isEmpty();
//
//        AccessToken value = createBaseToken(roomId, identity, sources, canPublish);
//        value.addGrants(new CanUpdateOwnMetadata(false));
//        value.setMetadata(metaDataEncoder.encode(ParticipantRole.PARTICIPANT));
//        return value.toJwt();
//    }
//
//    /**
//     * Возвращает список разрешённых источников после применения ограничений.
//     * BLOCK обрабатывается вызывающим кодом (403), здесь не участвует.
//     */
//    private List<String> applyRestrictions(List<RestrictionType> restrictions) {
//        List<String> sources = new ArrayList<>(ALL_PUBLISH_SOURCES);
//
//        if (restrictions.contains(RestrictionType.MUTE_MICROPHONE)) {
//            sources.remove("microphone");
//        }
//        if (restrictions.contains(RestrictionType.MUTE_CAMERA)) {
//            sources.remove("camera");
//        }
//        if (restrictions.contains(RestrictionType.MUTE_SCREEN_SHARE)) {
//            sources.remove("screen_share");
//            sources.remove("screen_share_audio");
//        }
//
//        return sources;
//    }
//
//    private AccessToken createBaseToken(
//            UUID roomId,
//            String identity,
//            List<String> publishSources,
//            boolean canPublish
//    ) {
//        AccessToken value = new AccessToken(
//                properties.getApiKey(),
//                properties.getApiSecret()
//        );
//        value.setIdentity(identity);
//        value.addGrants(
//                new RoomName(roomId.toString()),
//                new RoomJoin(true),
//                new CanSubscribe(true),
//                new CanPublishData(true)
//        );
//
//        if (canPublish) {
//            value.addGrants(new CanPublish(true));
//            if (!publishSources.isEmpty()) {
//                value.addGrants(new CanPublishSources(publishSources));
//            }
//        }
//
//        return value;
//    }
//}