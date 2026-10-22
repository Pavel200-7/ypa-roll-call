//package com.example.ypa_roll_call.roomold.mapper;
//
//import com.example.ypa_roll_call.roomold.data.response.RoomDto;
//import com.example.ypa_roll_call.roomold.jpa.Room;
//import org.mapstruct.InjectionStrategy;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingConstants;
//
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
//        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
//public interface RoomMapper {
//
//    @Mapping(source = "roomold.id", target = "roomId")
//    @Mapping(source = "roomold.adminId", target = "adminId")
//    RoomDto toDto(Room room);
//}
