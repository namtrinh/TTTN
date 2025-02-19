package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findByTheaterId(String theaterId);

    List<Room> findByRoomType(Room.RoomType roomType);

    boolean existsByRoomName(String name);
}
