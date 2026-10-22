package com.example.ypa_roll_call.room.infrastructure.auth;


import com.example.ypa_roll_call.room.application.port.out.CurrentUser;
import com.example.ypa_roll_call.room.domain.model.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrentUserAdapter implements CurrentUser {

    private final com.example.ypa_roll_call.auth.utils.CurrentUser currentUser;

    @Override
    public UserId getUserId() {
        return UserId.of(currentUser.getUserId());
    }
}
