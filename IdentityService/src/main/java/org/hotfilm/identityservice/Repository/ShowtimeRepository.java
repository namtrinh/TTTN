package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, String> {

    @Query(value = "SELECT showtime_id, FORMAT(time_start, 'hh:mm tt'), FORMAT(time_end, 'hh:mm tt')," +
            "room_room_id, movie_movie_id " +
            "FROM showtime " +
            "LEFT JOIN movie ON movie.movie_id = showtime.movie_movie_id " +
            "LEFT JOIN room ON room.room_id = showtime.room_room_id " +
            "WHERE FORMAT(time_start, 'yyyy-MM-dd') = :time " +
            "AND (:roomId IS NULL OR :roomId = '' OR room_room_id = :roomId) " +
            "ORDER BY time_start ASC", nativeQuery = true)
    List<ShowtimeResponse> findAllByTime(Date time, String roomId);


    @Query(value = "SELECT showtime_id, FORMAT(time_start, 'hh-mm tt'), FORMAT(time_end, 'hh-mm tt'), room_room_id\n" +
            "FROM showtime\n" +
            "INNER JOIN movie\n" +
            "ON movie.movie_id = showtime.movie_movie_id\n" +
            "WHERE movie_id = :movieId AND\n" +
            "      FORMAT(showtime.time_start, 'yyyy-MM-dd') = :time", nativeQuery = true)
    List<ShowtimeResponse> findAllByTimeAndMovie(Date time, String movieId);

    @Query(value = "SELECT s1.*\n" +
            "FROM showtime s1\n" +
            "LEFT JOIN showtime s2 \n" +
            "    ON s1.showtime_id != s2.showtime_id\n" +
            "    AND s1.time_start < :time_start\n" +
            "    AND s1.time_end > :time_end\n" +
            "WHERE s2.showtime_id IS NOT NULL;\n", nativeQuery = true)
    List<Showtime> existsShowtime(LocalDateTime time_start, LocalDateTime time_end);
}
