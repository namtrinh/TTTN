package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.ModelDTO.Response.SeatResponse;

import java.util.List;
import java.util.Set;

public interface SeatService {
    List<SeatResponse> findAll();

    Seat save(Seat seat);

    Set<Seat> createSeat(int row, int col);

    Seat findById(String seatId);

    boolean existsById(String seatId);

    void deleteById(String seatId);

    Seat updateById(String seatId, Seat seat);



    List<Seat> findBySeatStatus(SeatStatus seatStatus);
}
