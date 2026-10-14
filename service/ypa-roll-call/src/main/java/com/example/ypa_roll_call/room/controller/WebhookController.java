package com.example.ypa_roll_call.room.controller;

import com.example.ypa_roll_call.common.exception.base.BusinessRuleException;
import com.example.ypa_roll_call.room.service.WebhookEventHandlerFactory;
import io.livekit.server.WebhookReceiver;
import livekit.LivekitWebhook.WebhookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/livekit/webhook", consumes = "application/webhook+json")
public class WebhookController {
    private final WebhookReceiver webhookReceiver;
    private final WebhookEventHandlerFactory factory;

    @PostMapping
    public ResponseEntity<String> receiveWebhook(@RequestHeader("Authorization") String authHeader, @RequestBody String body) {
        WebhookEvent event;

        try {
            event = webhookReceiver.receive(body, authHeader);
        } catch (Exception e) {
            log.warn("Invalid webhook signature", e);
            throw new BusinessRuleException("Invalid webhook signature");
        }

        log.info("LiveKit Webhook: {}", event.getEvent());
        var handler = factory.getHandler(event).orElse(null);

        if (handler == null) {
            log.info("No handler for event type: {}", event.getEvent());
            return ResponseEntity.ok("ok");
        }

        try {
            handler.handle(event);
        } catch (Exception e) {
            log.error("Failed to handle webhook event {}", event.getEvent(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok("ok");
    }
}
