package org.hotfilm.backend.Service;

import org.hotfilm.backend.Model.Seat;
import org.hotfilm.backend.ModelDTO.Request.SeatRequest;
import org.hotfilm.backend.ModelDTO.Response.SeatResponse;

import java.util.List;
import java.util.Set;

public interface SeatService {
    List<SeatResponse> findAll(String showtimeId);

    Set<Seat> createSeat(int row, int col, String showtimeId);

    Seat findById(String seatId);

    void deleteByShowtimeId(String showtimeId);

    SeatResponse updateById(String seatId, SeatRequest seatRequest);

}
