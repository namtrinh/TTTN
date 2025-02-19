package org.hotfilm.identityservice.Mapper;

import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.ModelDTO.Request.RoomRequest;
import org.hotfilm.identityservice.ModelDTO.Response.RoomResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toRoom(RoomRequest roomRequest);

    RoomResponse toRoomResponse(Room room);
}
