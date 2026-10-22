package com.example.ypa_roll_call.room.web.controller;

import com.example.ypa_roll_call.room.application.command.BanParticipantCommand;
import com.example.ypa_roll_call.room.application.command.MuteParticipantCommand;
import com.example.ypa_roll_call.room.application.command.UnbanParticipantCommand;
import com.example.ypa_roll_call.room.application.command.UnmuteParticipantCommand;
import com.example.ypa_roll_call.room.application.usecase.BanParticipantUseCase;
import com.example.ypa_roll_call.room.application.usecase.MuteParticipantUseCase;
import com.example.ypa_roll_call.room.application.usecase.UnbanParticipantUseCase;
import com.example.ypa_roll_call.room.application.usecase.UnmuteParticipantUseCase;
import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import com.example.ypa_roll_call.room.web.dto.request.BanParticipantRequest;
import com.example.ypa_roll_call.room.web.dto.request.MuteParticipantRequest;
import com.example.ypa_roll_call.room.web.dto.request.UnbanParticipantRequest;
import com.example.ypa_roll_call.room.web.dto.request.UnmuteParticipantRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms/{roomId}/participants/{participantId}")
public class RoomModerationController {

    private final BanParticipantUseCase banParticipantUseCase;
    private final UnbanParticipantUseCase unbanParticipantUseCase;
    private final MuteParticipantUseCase muteParticipantUseCase;
    private final UnmuteParticipantUseCase unmuteParticipantUseCase;

    @PostMapping("/ban")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ban(@PathVariable UUID roomId, @PathVariable UUID participantId) {
        banParticipantUseCase.execute(new BanParticipantCommand(
                new RoomId(roomId),
                new ParticipantId(participantId)
        ));
    }

    @DeleteMapping("/ban")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unban(@PathVariable UUID roomId, @PathVariable UUID participantId) {
        unbanParticipantUseCase.execute(new UnbanParticipantCommand(
                new RoomId(roomId),
                new ParticipantId(participantId)
        ));
    }

    @PostMapping("/mute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void mute(@PathVariable UUID roomId, @PathVariable UUID participantId) {
        muteParticipantUseCase.execute(new MuteParticipantCommand(
                new RoomId(roomId),
                new ParticipantId(participantId)
        ));
    }

    @DeleteMapping("/mute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unmute(@PathVariable UUID roomId, @PathVariable UUID participantId) {
        unmuteParticipantUseCase.execute(new UnmuteParticipantCommand(
                new RoomId(roomId),
                new ParticipantId(participantId)
        ));
    }
}