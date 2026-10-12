package com.example.ypa_roll_call.common.exception.handler;

import com.example.ypa_roll_call.common.exception.base.BusinessRuleException;
import com.example.ypa_roll_call.common.exception.base.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 — ресурс не найден.
     * Ловит RoomNotFoundException и любые другие наследники ResourceNotFoundException.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        log.warn("Resource not found: {} at {}", e.getMessage(), request.getRequestURI());
        return build(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", e.getMessage(), request, null);
    }

    /**
     * 409 — нарушено бизнес-правило.
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(
            BusinessRuleException e,
            HttpServletRequest request
    ) {
        log.warn("Business rule violated: {} at {}", e.getMessage(), request.getRequestURI());
        return build(HttpStatus.CONFLICT, "BUSINESS_RULE_VIOLATION", e.getMessage(), request, null);
    }

    /**
     * 400 — ошибки валидации DTO (@Valid).
     * Собираем все ошибки полей в details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        Map<String, Object> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        err -> err.getField(),
                        err -> (Object) (err.getDefaultMessage() != null
                                ? err.getDefaultMessage()
                                : "invalid"),
                        (a, b) -> a
                ));

        log.warn("Validation failed at {}: {}", request.getRequestURI(), fieldErrors);
        return build(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_FAILED",
                "Validation failed",
                request,
                fieldErrors
        );
    }

    /**
     * 403 — доступ запрещён (Spring Security).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(
            AccessDeniedException e,
            HttpServletRequest request
    ) {
        log.warn("Access denied at {}: {}", request.getRequestURI(), e.getMessage());
        return build(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "Access denied", request, null);
    }

    /**
     * 401 — не аутентифицирован (Spring Security).
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(
            AuthenticationException e,
            HttpServletRequest request
    ) {
        log.warn("Authentication failed at {}: {}", request.getRequestURI(), e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "UNAUTHENTICATED", "Authentication required", request, null);
    }

    /**
     * 500 — всё остальное.
     * Логируем с полным стектрейсом, но клиенту не отдаём внутренние детали.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("Unexpected error at {}", request.getRequestURI(), e);
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Internal server error",
                request,
                null
        );
    }

    // --- Общий билдер ---

    private ResponseEntity<ApiError> build(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request,
            Map<String, Object> details
    ) {
        ApiError body = ApiError.builder()
                .status(status.value())
                .code(code)
                .message(message)
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .details(details)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}