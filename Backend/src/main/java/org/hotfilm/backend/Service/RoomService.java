package org.hotfilm.backend.Service;

import org.hotfilm.backend.Model.Room;
import org.hotfilm.backend.ModelDTO.Request.RoomRequest;
import org.hotfilm.backend.ModelDTO.Response.RoomResponse;

import java.util.List;

public interface RoomService {
    List<RoomResponse> findAll();

    RoomResponse save(Room room);

    RoomResponse findById(String roomId);

    boolean existsById(String roomId);

    void deleteById(String roomId);

    RoomResponse updateById(String roomId, RoomRequest roomRequest);

    List<Room> findByTheaterID(String theaterId);
    List<Room> findByRoomType(Room.RoomType roomType);
}
