package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.ModelDTO.Request.SeatRequest;
import org.hotfilm.identityservice.ModelDTO.Response.SeatResponse;

import java.util.List;
import java.util.Set;

public interface SeatService {
    List<SeatResponse> findAll(String showtimeId);

    Set<Seat> createSeat(int row, int col, String showtimeId);

    Seat findById(String seatId);

    void deleteByShowtimeId(String showtimeId);

    SeatResponse updateById(String seatId, SeatRequest seatRequest);

}
