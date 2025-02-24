package org.hotfilm.identityservice.Controller;

import org.hotfilm.identityservice.Mapper.RoomMapper;
import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.Model.Room.RoomType;
import org.hotfilm.identityservice.ModelDTO.Request.RoomRequest;
import org.hotfilm.identityservice.ModelDTO.Response.RoomResponse;
import org.hotfilm.identityservice.Repository.RoomRepository;
import org.hotfilm.identityservice.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public ApiResponse<List<RoomResponse>> getAllRooms() {
        return ApiResponse.<List<RoomResponse>>builder()
                .status(HttpStatus.OK)
                .result( roomService.findAll())
                .build();
    }

    @GetMapping("/{roomId}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable String roomId) {
        return ApiResponse.<RoomResponse>builder()
                .status(HttpStatus.OK)
                .result(roomService.findById(roomId))
                .build();
    }

    @PostMapping
    public ApiResponse<RoomResponse> createRoom(@RequestBody Room room) {
        return ApiResponse.<RoomResponse>builder()
                .status(HttpStatus.OK)
                .result(roomService.save(room))
                .build();
    }

    @PutMapping("/{roomId}")
    public ApiResponse<RoomResponse> updateRoom(
            @PathVariable String roomId,
            @RequestBody RoomRequest roomRequest) {

        return ApiResponse.<RoomResponse>builder()
                .status(HttpStatus.OK)
                .result(roomService.updateById(roomId, roomRequest))
                .build();
    }

    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable String roomId) {
        roomService.deleteById(roomId);
    }

    @GetMapping("/theater/{theaterId}")
    public ApiResponse<List<RoomResponse>> getRoomsByTheaterId(@PathVariable String theaterId) {
        List<Room> rooms = roomService.findByTheaterID(theaterId);
        List<RoomResponse> roomResponses = rooms.stream()
                .map(roomMapper::toRoomResponse)
                .toList();
        return ApiResponse.<List<RoomResponse>>builder()
                .status(HttpStatus.OK)
                .result(roomResponses)
                .build();
    }

    @GetMapping("/type/{roomType}")
    public ApiResponse<List<RoomResponse>> getRoomsByRoomType(@PathVariable RoomType roomType) {
        List<Room> rooms = roomService.findByRoomType(roomType);
        List<RoomResponse> roomResponses = rooms.stream()
                .map(roomMapper::toRoomResponse)
                .toList();
        return ApiResponse.<List<RoomResponse>>builder()
                .status(HttpStatus.OK)
                .result(roomResponses)
                .build();
    }
}
