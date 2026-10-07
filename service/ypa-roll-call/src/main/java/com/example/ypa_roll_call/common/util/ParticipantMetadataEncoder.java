package com.example.ypa_roll_call.common.util;

import com.example.ypa_roll_call.common.enums.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParticipantMetadataEncoder {
    private final ObjectMapper objectMapper;

    public String encode(Metadata metadata) {
        try {
            return objectMapper.writeValueAsString(Map.of(metadata.key(), metadata.value()));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to encode participant metadata", e);
        }
    }

    public Optional<String> decodeValue(String raw, Metadata metadata) {
        if (raw == null || raw.isBlank()) return Optional.empty();
        try {
            var value = objectMapper.readTree(raw)
                    .path(metadata.key())
                    .asText(null);
            return Optional.ofNullable(value);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
