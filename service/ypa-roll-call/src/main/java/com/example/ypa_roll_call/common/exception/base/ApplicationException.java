package com.example.ypa_roll_call.common.exception.base;

public abstract class ApplicationException extends RuntimeException {
    protected ApplicationException(String message) { super(message); }
    protected ApplicationException(String message, Throwable cause) { super(message, cause); }
}
