package com.example.ypa_roll_call.room.service;

import livekit.LivekitWebhook.WebhookEvent;

import java.util.Optional;

public interface WebhookEventHandlerFactory {
    Optional<WebhookEventHandler> getHandler(WebhookEvent event);
}
