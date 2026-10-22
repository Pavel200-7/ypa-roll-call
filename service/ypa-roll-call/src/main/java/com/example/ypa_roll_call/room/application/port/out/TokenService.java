package com.example.ypa_roll_call.room.application.port.out;

import com.example.ypa_roll_call.room.domain.model.vo.ParticipantId;
import com.example.ypa_roll_call.room.domain.model.vo.Restrictions;
import com.example.ypa_roll_call.room.domain.model.vo.RoomId;
import com.example.ypa_roll_call.room.domain.model.vo.Token;

public interface TokenService {
    Token getAdminToken(RoomId roomId, ParticipantId participantId);
    Token getParticipantToken(RoomId roomId, ParticipantId participantId, Restrictions restrictions);
}
