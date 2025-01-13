package org.hotfilm.identityservice.Controller;

import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.Model.Room.RoomType;
import org.hotfilm.identityservice.ModelDTO.Request.RoomRequest;
import org.hotfilm.identityservice.ModelDTO.Response.RoomResponse;
import org.hotfilm.identityservice.Service.RoomService;
import org.hotfilm.identityservice.Mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.findAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String roomId) {
        Room room = roomService.findById(roomId);
        return ResponseEntity.ok(roomMapper.toRoomResponse(room));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest roomRequest) {
        Room room = roomMapper.toRoom(roomRequest);
        Room savedRoom = roomService.save(room);
        return ResponseEntity.ok(roomMapper.toRoomResponse(savedRoom));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable String roomId,
            @RequestBody RoomRequest roomRequest) {
        Room room = roomMapper.toRoom(roomRequest);
        Room updatedRoom = roomService.updateById(roomId, room);
        return ResponseEntity.ok(roomMapper.toRoomResponse(updatedRoom));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomId) {
        roomService.deleteById(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByTheaterId(@PathVariable String theaterId) {
        List<Room> rooms = roomService.findByTheaterID(theaterId);
        List<RoomResponse> roomResponses = rooms.stream()
                .map(roomMapper::toRoomResponse)
                .toList();
        return ResponseEntity.ok(roomResponses);
    }

    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<RoomResponse>> getRoomsByRoomType(@PathVariable RoomType roomType) {
        List<Room> rooms = roomService.findByRoomType(roomType);
        List<RoomResponse> roomResponses = rooms.stream()
                .map(roomMapper::toRoomResponse)
                .toList();
        return ResponseEntity.ok(roomResponses);
    }
}
