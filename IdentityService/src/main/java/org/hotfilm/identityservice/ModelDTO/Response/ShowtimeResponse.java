package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Room;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeResponse implements Serializable {
    private String showtimeId;

    private String movieId;

    private String movieName;

    private String time_start;

    private String time_end;


    private Movie movie;

    public ShowtimeResponse(String showtimeId, String movieId, String movieName, String time_start, String time_end) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    //  private RoomResponse room;
}
