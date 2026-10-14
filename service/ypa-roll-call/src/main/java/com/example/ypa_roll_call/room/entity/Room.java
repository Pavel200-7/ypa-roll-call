package com.example.ypa_roll_call.room.entity;

import com.example.ypa_roll_call.common.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "rooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "admin_id", nullable = false, updatable = false)
    private UUID adminId;

    private Room(UUID adminId) {
        this.id = UUID.randomUUID();
        this.adminId = adminId;
    }

    public static Room createRoom(UUID adminId) {
        return new Room(adminId);
    }

    public String getStringId() {
        return this.id.toString();
    }
}
