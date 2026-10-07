package com.example.ypa_roll_call.connection_token.controller;

import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenResponse;
import com.example.ypa_roll_call.connection_token.service.ConnectionTokenService;
import com.example.ypa_roll_call.common.data.RoomOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/connection-tokens")
public class ConnectionTokenController {
    private final ConnectionTokenService connectionTokenService;

    @PostMapping("/admin")
    public ResponseEntity<ConnectionTokenResponse> getAdminToken(@RequestBody RoomOptions options) throws IOException {
        ConnectionTokenResponse connectionToken = connectionTokenService.getAdminConnectionToken(options);
        return ResponseEntity.ok(connectionToken);
    }

    @GetMapping("/participant/{roomId}")
    public ResponseEntity<ConnectionTokenResponse> getParticipantToken(@PathVariable String roomId) throws IOException {
        ConnectionTokenResponse connectionToken = connectionTokenService.getParticipantConnectionToken(roomId);
        return ResponseEntity.ok(connectionToken);
    }
}
