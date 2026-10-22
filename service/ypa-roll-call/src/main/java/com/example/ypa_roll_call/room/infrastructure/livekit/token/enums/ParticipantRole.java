package com.example.ypa_roll_call.room.infrastructure.livekit.token.enums;

public enum ParticipantRole implements Metadata {
    ORGANIZER, PARTICIPANT;

    public String key() {
        return "role";
    }

    public String value() {
        return name().toLowerCase();
    }
}