package org.hotfilm.identityservice.ModelDTO.Request;

import org.hotfilm.identityservice.Model.Room.RoomType;

import java.util.Set;

public class RoomRequest {
    private String roomName;
    private int totalSeat;
    private String theaterId;
    private double roomPrice;
    private RoomType roomType;
    private Set<Long> seatIds;
}
