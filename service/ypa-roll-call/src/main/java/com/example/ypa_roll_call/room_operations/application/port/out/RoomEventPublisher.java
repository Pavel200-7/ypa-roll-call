package com.example.ypa_roll_call.room_operations.application.port.out;

import com.example.ypa_roll_call.common.domain.event.DomainEvent;

public interface RoomEventPublisher {
    void publish(DomainEvent event);
}
