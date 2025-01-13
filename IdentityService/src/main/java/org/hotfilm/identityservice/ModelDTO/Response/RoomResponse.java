package org.hotfilm.identityservice.ModelDTO.Response;

import org.hotfilm.identityservice.Model.Room.RoomType;
import org.hotfilm.identityservice.Model.Seat;

import java.util.Set;

public class RoomResponse {
    private String roomId;
    private String roomName;
    private int totalSeat;
    private String theaterId;
    private double roomPrice;
    private RoomType roomType;
    private Set<Seat> seats;
}
