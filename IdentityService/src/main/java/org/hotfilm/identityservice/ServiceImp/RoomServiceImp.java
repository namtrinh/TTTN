package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Mapper.RoomMapper;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.ModelDTO.Request.RoomRequest;
import org.hotfilm.identityservice.ModelDTO.Response.RoomResponse;
import org.hotfilm.identityservice.Repository.RoomRepository;
import org.hotfilm.identityservice.Service.RoomService;
import org.hotfilm.identityservice.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RoomServiceImp implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private SeatService seatService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Room> hashOperations;

    private static final String HASH_ROOM = "room";

    public RoomServiceImp(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private void setTTL() {
        redisTemplate.expire(HASH_ROOM, 10, TimeUnit.MINUTES);
    }

    @Override
    @Transactional
    public List<RoomResponse> findAll() {
        if (redisTemplate.hasKey(HASH_ROOM)) {
            Map<String, Room> roomMap = hashOperations.entries(HASH_ROOM);
            List<Room> rooms = roomMap.values()
                    .stream()
                    .map(value -> (Room) value)
                    .collect(Collectors.toList());
            return rooms.stream()
                    .map(roomMapper::toRoomResponse)
                    .collect(Collectors.toList());
        } else {
            List<Room> rooms = roomRepository.findAll();
            for (Room room : rooms) {
                hashOperations.put(HASH_ROOM, room.getRoomId(), room);
            }
            return rooms.stream()
                    .map(roomMapper::toRoomResponse)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public RoomResponse save(Room room) {
        if (roomRepository.existsByRoomName(room.getRoomName())) {
            throw new RuntimeException("Room name has been exists");
        }
        switch (room.getRoomType().toString()){
            case "STANDARD":
                room.setSeat(seatService.createSeat(8, 8));
                room.setTotalSeat(64);
                break;
            case "IMAX":
                room.setSeat(seatService.createSeat(10, 10));
                room.setTotalSeat(100);
                break;
            case "COUPLE":
                room.setSeat(seatService.createSeat(6,7));
                room.setTotalSeat(42);
                break;
        }
        roomRepository.save(room);
        hashOperations.put(HASH_ROOM, room.getRoomId(), room);
        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponse findById(String roomId) {
        if (hashOperations.hasKey(HASH_ROOM, roomId)) {
            return roomMapper.toRoomResponse(hashOperations.get(HASH_ROOM, roomId));
        } else {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room with ID " + roomId + " not found"));

            System.out.println(room.getRoomId());
            hashOperations.put(HASH_ROOM, room.getRoomId(), room);
            return roomMapper.toRoomResponse(room);
        }
    }

    @Override
    public boolean existsById(String roomId) {
        return roomRepository.existsById(roomId);
    }

    @Override
    public void deleteById(String roomId) {
        if (hashOperations.hasKey(HASH_ROOM, roomId)) {
            hashOperations.delete(HASH_ROOM, roomId);
        }
        roomRepository.deleteById(roomId);
    }

    @Override
    public RoomResponse updateById(String roomId, RoomRequest roomRequest) {
        Room room = roomMapper.toRoom(roomRequest);
        room.setRoomId(roomId);
        if (hashOperations.hasKey(HASH_ROOM, room.getRoomId())) {
            hashOperations.put(HASH_ROOM, room.getRoomId(), room);
        }
        room.setRoomId(roomId);
        roomRepository.save(room);
        return roomMapper.toRoomResponse(room);
    }

    @Override
    public List<Room> findByTheaterID(String theaterId) {
        return List.of();
    }

    @Override
    public List<Room> findByRoomType(Room.RoomType roomType) {
        return List.of();
    }
}
