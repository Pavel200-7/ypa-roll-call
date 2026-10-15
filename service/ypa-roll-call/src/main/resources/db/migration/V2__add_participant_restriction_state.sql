CREATE TABLE IF NOT EXISTS participant_restrictions (
    id UUID PRIMARY KEY,
    room_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_restrictions_room
        FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    CONSTRAINT uk_restriction_participant
        UNIQUE (room_id, participant_id)
);

CREATE TABLE IF NOT EXISTS participant_restriction_types (
    restriction_id UUID NOT NULL,
    restriction_type SMALLINT NOT NULL,
    CONSTRAINT fk_restriction_types_restriction
        FOREIGN KEY (restriction_id) REFERENCES participant_restrictions(id) ON DELETE CASCADE,
    CONSTRAINT uk_restriction_type
        PRIMARY KEY (restriction_id, restriction_type)
);