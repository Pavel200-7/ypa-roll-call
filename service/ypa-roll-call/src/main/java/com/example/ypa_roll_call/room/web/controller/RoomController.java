package com.example.ypa_roll_call.room.web.controller;

import com.example.ypa_roll_call.room.application.command.CloseRoomCommand;
import com.example.ypa_roll_call.room.application.command.CreateRoomCommand;
import com.example.ypa_roll_call.room.application.usecase.CloseRoomUseCase;
import com.example.ypa_roll_call.room.application.usecase.CreateRoomUseCase;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import com.example.ypa_roll_call.room.domain.model.vo.Token;
import com.example.ypa_roll_call.room.web.dto.response.CreateRoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private final CreateRoomUseCase createRoomUseCase;
    private final CloseRoomUseCase closeRoomUseCase;

    @PostMapping
    public ResponseEntity<CreateRoomResponse> createRoom() {
        Token token = createRoomUseCase.execute(new CreateRoomCommand());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateRoomResponse(token.value()));
    }

    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeRoom(@PathVariable UUID roomId) {
        CloseRoomCommand command = CloseRoomCommand.of(
                RoomId.of(roomId));
        closeRoomUseCase.execute(command);
    }
}
