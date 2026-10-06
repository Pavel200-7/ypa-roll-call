package com.example.ypa_roll_call.auth.utils;

public final class Claims {
    public static final String USER_ID = "sub";
    public static final String USER_NAME = "preferred_username";
    public static final String USER_EMAIL = "email";

    private Claims() {
        throw new UnsupportedOperationException("Creation of this object is unsupported");
    }
}