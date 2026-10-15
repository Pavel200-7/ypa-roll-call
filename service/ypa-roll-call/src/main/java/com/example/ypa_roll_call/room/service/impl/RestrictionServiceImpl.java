package com.example.ypa_roll_call.room.service.impl;

import com.example.ypa_roll_call.auth.utils.CurrentUser;
import com.example.ypa_roll_call.common.logging.annotation.LogOperation;
import com.example.ypa_roll_call.room.data.common.RestrictionDto;
import com.example.ypa_roll_call.room.data.request.GetRestrictionsDto;
import com.example.ypa_roll_call.room.data.response.RestrictionsDto;
import com.example.ypa_roll_call.room.entity.ParticipantRestriction;
import com.example.ypa_roll_call.room.enums.RestrictionType;
import com.example.ypa_roll_call.room.repository.ParticipantRestrictionRepository;
import com.example.ypa_roll_call.room.service.RestrictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestrictionServiceImpl implements RestrictionService {

    private final ParticipantRestrictionRepository repository;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    @LogOperation("ADD_RESTRICTION")
    public void addRestriction(RestrictionDto dto) {
        ParticipantRestriction restriction = repository
                .findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
                .orElseGet(() -> repository.save(
                        new ParticipantRestriction(
                                dto.getRoomId(),
                                dto.getParticipantId())));

        restriction.addRestriction(dto.getType());
        repository.save(restriction);
    }

    @Override
    @Transactional
    @LogOperation("REMOVE_RESTRICTION")
    public void removeRestriction(RestrictionDto dto) {
        repository.findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
                .ifPresent(r -> {
                    r.removeRestriction(dto.getType());
                    if (r.isEmpty()) {
                        repository.delete(r);
                    } else {
                        repository.save(r);
                    }
                });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRestriction(RestrictionDto dto) {
        return repository.findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
                .map(r -> r.hasRestriction(dto.getType()))
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    @LogOperation("GET_RESTRICTIONS")
    public RestrictionsDto getRestrictions(GetRestrictionsDto dto) {
        List<RestrictionType> types = repository
                .findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
                .map(r -> List.copyOf(r.getRestrictions()))
                .orElseGet(List::of);

        return new RestrictionsDto(
                dto.getRoomId(),
                dto.getParticipantId(),
                types
        );
    }

    @Transactional(readOnly = true)
    public boolean hasAnyRestriction(UUID roomId, UUID participantId) {
        return repository.existsByRoomIdAndParticipantId(roomId, participantId);
    }

    @Transactional(readOnly = true)
    public Optional<ParticipantRestriction> findRestriction(UUID roomId, UUID participantId) {
        return repository.findByRoomIdAndParticipantId(roomId, participantId);
    }
}