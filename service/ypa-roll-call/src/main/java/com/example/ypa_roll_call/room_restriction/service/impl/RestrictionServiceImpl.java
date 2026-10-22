//package com.example.ypa_roll_call.room_restriction.service.impl;
//
//import com.example.ypa_roll_call.auth.utils.CurrentUser;
//import com.example.ypa_roll_call.common.exception.room.RoomNotFoundException;
//import com.example.ypa_roll_call.common.annotation.annotation.logging.LogOperation;
//import com.example.ypa_roll_call.room_restriction.data.common.RestrictionDto;
//import com.example.ypa_roll_call.room_restriction.data.request.GetRestrictionsDto;
//import com.example.ypa_roll_call.room_restriction.data.common.RestrictionsDto;
//import com.example.ypa_roll_call.room_restriction.domain.ParticipantRestriction;
//import com.example.ypa_roll_call.roomold.jpa.Room;
//import com.example.ypa_roll_call.room_restriction.enums.RestrictionType;
//import com.example.ypa_roll_call.room_restriction.repository.ParticipantRestrictionRepository;
//import com.example.ypa_roll_call.roomold.repository.RoomRepository;
//import com.example.ypa_roll_call.room_restriction.service.RestrictionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class RestrictionServiceImpl implements RestrictionService {
//
//    private final ParticipantRestrictionRepository repository;
//    private final RoomRepository roomRepository;
//    private final CurrentUser currentUser;
//
//    @Override
//    @Transactional
//    @LogOperation("ADD_RESTRICTION")
//    public void addRestriction(RestrictionDto dto) {
//        Room room = roomRepository.findById(dto.getRoomId())
//                .orElseThrow(() -> new RoomNotFoundException(dto.getRoomId()));
//
//        if (!room.isAdmin(currentUser.getUserId())) {
//            throw new AccessDeniedException("Only roomold admin can add restrictions");
//        }
//
//        ParticipantRestriction restriction = repository
//                .findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
//                .orElseGet(() -> ParticipantRestriction.createRestriction(room, dto.getParticipantId()));
//        restriction.addRestriction(dto.getType());
//        repository.save(restriction);
//    }
//
//    @Override
//    @Transactional
//    @LogOperation("REMOVE_RESTRICTION")
//    public void removeRestriction(RestrictionDto dto) {
//        Room room = roomRepository.findById(dto.getRoomId())
//                .orElseThrow(() -> new RoomNotFoundException(dto.getRoomId()));
//        if (!room.isAdmin(currentUser.getUserId())) {
//            throw new AccessDeniedException("Only roomold admin can add restrictions");
//        }
//        repository.findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
//                .ifPresent(r -> {
//                    r.removeRestriction(dto.getType());
//                    if (r.isEmpty()) {
//                        repository.delete(r);
//                    } else {
//                        repository.save(r);
//                    }
//                });
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    @LogOperation("HAS_RESTRICTION")
//    public boolean hasRestriction(RestrictionDto dto) {
//        return repository.findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
//                .map(r -> r.hasRestriction(dto.getType()))
//                .orElse(false);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    @LogOperation("GET_RESTRICTIONS")
//    public RestrictionsDto getRestrictions(GetRestrictionsDto dto) {
//        List<RestrictionType> types = repository
//                .findByRoomIdAndParticipantId(dto.getRoomId(), dto.getParticipantId())
//                .map(r -> List.copyOf(r.getRestrictions()))
//                .orElseGet(List::of);
//
//        return new RestrictionsDto(
//                dto.getRoomId(),
//                dto.getParticipantId(),
//                types
//        );
//    }
//
//    @Transactional(readOnly = true)
//    @LogOperation("HAS_ANY_RESTRICTION")
//    public boolean hasAnyRestriction(UUID roomId, UUID participantId) {
//        return repository.existsByRoomIdAndParticipantId(roomId, participantId);
//    }
//
//    @Transactional(readOnly = true)
//    @LogOperation("FIND_RESTRICTION")
//    public Optional<ParticipantRestriction> findRestriction(UUID roomId, UUID participantId) {
//        return repository.findByRoomIdAndParticipantId(roomId, participantId);
//    }
//}