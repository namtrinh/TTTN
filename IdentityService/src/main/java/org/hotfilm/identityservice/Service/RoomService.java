package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.ModelDTO.Response.RoomResponse;

import java.util.List;

public interface RoomService {
    List<RoomResponse> findAll();

    Room save(Room room);

    Room findById(String roomId);

    boolean existsById(String roomId);

    void deleteById(String roomId);

    Room updateById(String roomId, Room room);

    List<Room> findByTheaterID(String theaterId);
    List<Room> findByRoomType(Room.RoomType roomType);
}
