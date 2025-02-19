package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.ModelDTO.Request.RoomRequest;
import org.hotfilm.identityservice.ModelDTO.Response.RoomResponse;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    Room save(Room room);

    RoomResponse findById(String roomId);

    boolean existsById(String roomId);

    void deleteById(String roomId);

    RoomResponse updateById(String roomId, RoomRequest roomRequest);

    List<Room> findByTheaterID(String theaterId);
    List<Room> findByRoomType(Room.RoomType roomType);
}
