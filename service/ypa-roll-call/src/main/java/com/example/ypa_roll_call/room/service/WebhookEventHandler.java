package com.example.ypa_roll_call.room.service;

import livekit.LivekitWebhook.WebhookEvent;

public interface WebhookEventHandler {
    void handle(WebhookEvent event);
    String supportedEventType();
}
