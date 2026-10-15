package com.example.ypa_roll_call.connection_token.controller;

import com.example.ypa_roll_call.connection_token.data.response.ConnectionTokenDto;
import com.example.ypa_roll_call.connection_token.service.ConnectionTokenService;
import com.example.ypa_roll_call.room.data.common.RoomOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/connection-tokens")
public class ConnectionTokenController {
    private final ConnectionTokenService connectionTokenService;

    @PostMapping("/admin")
    public ResponseEntity<ConnectionTokenDto> getAdminToken(@RequestBody RoomOptions options) {
        ConnectionTokenDto connectionToken = connectionTokenService.getAdminConnectionToken(options);
        return ResponseEntity.ok(connectionToken);
    }

    @GetMapping("/participant/{roomId}")
    public ResponseEntity<ConnectionTokenDto> getParticipantToken(@PathVariable UUID roomId) {
        ConnectionTokenDto connectionToken = connectionTokenService.getParticipantConnectionToken(roomId);
        return ResponseEntity.ok(connectionToken);
    }
}
