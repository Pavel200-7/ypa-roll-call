package com.example.ypa_roll_call.common.conf;

import io.livekit.server.LiveKitAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LiveKitAPIServiceConfiguration {

    private final LiveKitProperties properties;

    @Bean
    public LiveKitAPI api() {
        return LiveKitAPI.createClient(
                properties.getUrl(),
                properties.getApiKey(),
                properties.getApiSecret());
    }
}
