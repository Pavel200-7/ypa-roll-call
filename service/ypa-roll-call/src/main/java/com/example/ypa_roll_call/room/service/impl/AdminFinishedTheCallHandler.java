package com.example.ypa_roll_call.room.service.impl;

import com.example.ypa_roll_call.common.enums.ParticipantRole;
import com.example.ypa_roll_call.common.logging.annotation.LogOperation;
import com.example.ypa_roll_call.common.util.ParticipantMetadataEncoder;
import com.example.ypa_roll_call.room.service.RoomService;
import com.example.ypa_roll_call.room.service.WebhookEventHandler;
import livekit.LivekitWebhook.WebhookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminFinishedTheCallHandler implements WebhookEventHandler {
    private final RoomService roomService;
    private final ParticipantMetadataEncoder encoder;

    @Override
    public String supportedEventType() {
        return "admin_finished_call";
    }

    @Override
    @LogOperation("ADMIN_FINISHED_CALL_HANDLER")
    public void handle(WebhookEvent event) {
        String metadata = event.getParticipant().getMetadata();
        encoder.decodeValue(metadata, ParticipantRole.ADMIN)
                .filter(value -> value.equalsIgnoreCase(ParticipantRole.ADMIN.value()))
                .ifPresentOrElse(
                        v -> {
                            UUID roomId = UUID.fromString(event.getRoom().getName());
                            roomService.dropRoom(roomId);
                            },
                        () -> log.debug("Not an admin")
                );
    }
}