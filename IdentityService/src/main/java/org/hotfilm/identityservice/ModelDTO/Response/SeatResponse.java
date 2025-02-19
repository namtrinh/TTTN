package org.hotfilm.identityservice.ModelDTO.Response;

import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.Model.Seat.SeatType;

public class SeatResponse {
    private String seatId;
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus seatStatus;
    private String theaterId;
}
