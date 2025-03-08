package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.Room;
import org.hotfilm.backend.ModelDTO.Request.RoomRequest;
import org.hotfilm.backend.ModelDTO.Response.RoomResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toRoom(RoomRequest roomRequest);

    RoomResponse toRoomResponse(Room room);
}
