package com.example.ypa_roll_call.room.infrastructure.event;

import com.example.ypa_roll_call.room.application.port.out.RoomEventPublisher;
import com.example.ypa_roll_call.common.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringRoomEventPublisher implements RoomEventPublisher {
    private final ApplicationEventPublisher springPublisher;

    @Override
    public void publish(DomainEvent event) {
        springPublisher.publishEvent(event);
    }
}