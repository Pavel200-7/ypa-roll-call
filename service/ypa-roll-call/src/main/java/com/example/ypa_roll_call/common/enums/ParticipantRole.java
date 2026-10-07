package com.example.ypa_roll_call.common.enums;

public enum ParticipantRole implements Metadata {
    ADMIN, PARTICIPANT;

    public String key() {
        return "role";
    }

    public String value() {
        return name().toLowerCase();
    }
}