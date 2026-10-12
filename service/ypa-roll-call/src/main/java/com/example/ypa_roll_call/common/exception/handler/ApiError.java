package com.example.ypa_roll_call.common.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    /** HTTP статус (404, 409, 500 и т.д.) */
    private int status;

    /** Машиночитаемый код ошибки, например "ROOM_NOT_FOUND" */
    private String code;

    /** Человекочитаемое сообщение */
    private String message;

    /** Путь запроса, где произошла ошибка */
    private String path;

    /** Время ошибки */
    private Instant timestamp;

    /** Дополнительные детали (например, roomId, поле валидации) */
    private Map<String, Object> details;
}