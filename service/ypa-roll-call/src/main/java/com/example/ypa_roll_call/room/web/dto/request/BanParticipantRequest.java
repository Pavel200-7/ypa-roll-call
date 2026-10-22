package com.example.ypa_roll_call.room.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BanParticipantRequest(
        @NotNull UUID participantId
) {}