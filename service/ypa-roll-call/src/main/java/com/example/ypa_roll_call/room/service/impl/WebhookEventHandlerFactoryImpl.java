package com.example.ypa_roll_call.room.service.impl;

import com.example.ypa_roll_call.room.service.WebhookEventHandler;
import com.example.ypa_roll_call.room.service.WebhookEventHandlerFactory;
import livekit.LivekitWebhook.WebhookEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WebhookEventHandlerFactoryImpl implements WebhookEventHandlerFactory {
    private final Map<String, WebhookEventHandler> handlers;

    public WebhookEventHandlerFactoryImpl(List<WebhookEventHandler> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(WebhookEventHandler::supportedEventType, h -> h));
    }

    public Optional<WebhookEventHandler> getHandler(WebhookEvent event) {
        return Optional.ofNullable(handlers.get(event.getEvent()));
    }
}
