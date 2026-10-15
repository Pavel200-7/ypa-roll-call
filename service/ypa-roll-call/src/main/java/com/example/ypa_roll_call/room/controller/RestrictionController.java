package com.example.ypa_roll_call.room.controller;

import com.example.ypa_roll_call.room.data.common.RestrictionDto;
import com.example.ypa_roll_call.room.enums.RestrictionType;
import com.example.ypa_roll_call.room.service.RestrictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms/{roomId}/restrictions")
public class RestrictionController {

    private final RestrictionService restrictionService;

    @PostMapping("/{participantId}")
    public ResponseEntity<Void> addRestriction(
            @PathVariable UUID roomId,
            @PathVariable UUID participantId,
            @RequestParam RestrictionType type
    ) {
        RestrictionDto dto = new RestrictionDto(roomId, participantId,type);
        restrictionService.addRestriction(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> removeRestriction(
            @PathVariable UUID roomId,
            @PathVariable UUID participantId,
            @RequestParam RestrictionType type
    ) {
        RestrictionDto dto = new RestrictionDto(roomId, participantId, type);
        restrictionService.removeRestriction(dto);
        return ResponseEntity.noContent().build();
    }
}