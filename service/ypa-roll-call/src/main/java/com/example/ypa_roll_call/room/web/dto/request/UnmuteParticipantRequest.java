package com.example.ypa_roll_call.room.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnmuteParticipantRequest(
        @NotNull UUID participantId
) {}