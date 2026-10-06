package com.example.ypa_roll_call.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class JwtHelper {

    public String extractClaim(String payload, String claimName) {
        String searchPattern = "\"" + claimName + "\":\"";
        int startIndex = payload.indexOf(searchPattern);

        if (startIndex != -1) {
            startIndex += searchPattern.length();
            int endIndex = payload.indexOf("\"", startIndex);
            if (endIndex != -1) {
                return payload.substring(startIndex, endIndex);
            }
        }

        searchPattern = "\"" + claimName + "\":";
        startIndex = payload.indexOf(searchPattern);
        if (startIndex != -1) {
            startIndex += searchPattern.length();
            int endIndex = payload.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = payload.indexOf("}", startIndex);
            }
            if (endIndex != -1) {
                String value = payload.substring(startIndex, endIndex);
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                return value;
            }
        }

        return null;
    }

    public UUID extractUserId(String accessToken) {
        try {
            String[] parts = accessToken.split("\\.");
            if (parts.length > 1) {
                String payload = new String(Base64.getDecoder().decode(parts[1]));
                String sub = extractClaim(payload, "sub");
                if (sub != null) {
                    return UUID.fromString(sub);
                }
            }
        } catch (Exception e) {
            log.error("Failed to extract user id from token: {}", e.getMessage());
        }
        return UUID.randomUUID();
    }

    public String extractEmail(String accessToken) {
        try {
            String[] parts = accessToken.split("\\.");
            if (parts.length > 1) {
                String payload = new String(Base64.getDecoder().decode(parts[1]));
                return extractClaim(payload, "email");
            }
        } catch (Exception e) {
            log.error("Failed to extract email from token: {}", e.getMessage());
        }
        return null;
    }
}