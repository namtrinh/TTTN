package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

    List<Seat> findBySeatStatus(SeatStatus seatStatus);


}
