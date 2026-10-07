package com.example.ypa_roll_call.room.service.impl;

import com.example.ypa_roll_call.common.enums.ParticipantRole;
import com.example.ypa_roll_call.common.util.ParticipantMetadataEncoder;
import com.example.ypa_roll_call.room.service.RoomService;
import com.example.ypa_roll_call.room.service.WebhookEventHandler;
import livekit.LivekitWebhook.WebhookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomLeftHandler implements WebhookEventHandler {
    private final RoomService roomService;
    private final ParticipantMetadataEncoder encoder;

    @Override
    public String supportedEventType() {
        return "participant_left";
    }

    @Override
    public void handle(WebhookEvent event) {
        String metadata = event.getParticipant().getMetadata();
        encoder.decodeValue(metadata, ParticipantRole.ADMIN)
                .filter(value -> value.equalsIgnoreCase(ParticipantRole.ADMIN.value()))
                .ifPresentOrElse(
                        v -> {
                                try {
                                    roomService.dropRoom(event.getRoom().getSid());
                                } catch (IOException e) {
                                    log.error("Failed to drop room {}", event.getRoom().getSid(), e);
                                    throw new RuntimeException("Failed to drop room " + event.getRoom().getSid(), e);
                                }
                            },
                        () -> log.debug("Not an admin")
                );
    }
}