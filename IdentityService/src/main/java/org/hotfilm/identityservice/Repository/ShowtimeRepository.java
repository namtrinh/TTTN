package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, String> {

    @Query(value = "SELECT showtime_id, FORMAT(time_start, 'hh-mm')\n" +
            "FROM showtime\n" +
            "WHERE FORMAT(time_start, 'yyyy-MM-dd') = :time", nativeQuery = true)
    List<ShowtimeResponse> findAllByTime(Date time);
}
