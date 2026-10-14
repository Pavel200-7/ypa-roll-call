package com.example.ypa_roll_call.room.mapper;

import com.example.ypa_roll_call.room.data.response.RoomDto;
import com.example.ypa_roll_call.room.entity.Room;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoomMapper {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.adminId", target = "adminId")
    RoomDto toDto(Room room);
}
