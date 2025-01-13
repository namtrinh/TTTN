package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.Model.Seat.SeatType;
import org.hotfilm.identityservice.ModelDTO.Response.SeatResponse;

import java.util.List;

public interface SeatService {
    List<SeatResponse> findAll();

    Seat save(Seat seat);

    Seat findById(String seatId);

    boolean existsById(String seatId);

    void deleteById(String seatId);

    Seat updateById(String seatId, Seat seat);

    List<Seat> findBySeatType(SeatType seatType);

    List<Seat> findBySeatStatus(SeatStatus seatStatus);
}
