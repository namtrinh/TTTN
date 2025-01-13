package org.hotfilm.identityservice.ModelDTO.Request;

import org.hotfilm.identityservice.Model.Seat.SeatType;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;

public class SeatRequest {
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus seatStatus;
    private String theaterId;
}
