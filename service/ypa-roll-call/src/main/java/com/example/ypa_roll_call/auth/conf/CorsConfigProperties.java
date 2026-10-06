package com.example.ypa_roll_call.auth.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.cors")
public class CorsConfigProperties {
    private List<String> allowedOrigins = List.of();;
    private List<String> allowedMethods = List.of("GET", "POST");
}
