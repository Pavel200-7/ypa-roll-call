package com.example.ypa_roll_call.common.exception.base;

public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException(String message) { super(message); }
    public ResourceNotFoundException(String message, Exception cause) { super(message, cause); }

}
