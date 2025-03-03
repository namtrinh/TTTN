package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

    List<Seat> findBySeatStatus(SeatStatus seatStatus);

    void deleteAllByShowtimeId(String showtimeId);

    @Query(value = "select * from seat where showtime_id = :showtimeId ORDER BY seat_number ASC",nativeQuery = true)
    List<Seat> findAllByShowtimeId(String showtimeId);
}
