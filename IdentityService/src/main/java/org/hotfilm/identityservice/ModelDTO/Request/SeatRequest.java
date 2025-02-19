package org.hotfilm.identityservice.ModelDTO.Request;

import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.Model.Seat.SeatType;

public class SeatRequest {
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus seatStatus;
    private String theaterId;
}
