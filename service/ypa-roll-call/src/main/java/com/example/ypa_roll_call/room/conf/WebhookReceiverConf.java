package com.example.ypa_roll_call.room.conf;

import com.example.ypa_roll_call.common.conf.LiveKitProperties;
import io.livekit.server.WebhookReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@RequiredArgsConstructor
public class WebhookReceiverConf {
    private final LiveKitProperties properties;

    @Bean
    public WebhookReceiver webhookReceiver() {
        return new WebhookReceiver(properties.getApiKey(), properties.getApiSecret());
    }
}
