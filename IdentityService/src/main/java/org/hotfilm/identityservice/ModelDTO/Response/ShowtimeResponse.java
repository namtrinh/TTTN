package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Room;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeResponse implements Serializable {
    private String showtimeId;

    private String movieId;

    private String movieName;

    private String roomId;

    private String roomName;

    private String time_start;

    private String time_end;


    private Movie movie;
    private Room room;

    public ShowtimeResponse(String showtimeId, String movieId, String movieName, String roomId,
                            String roomName, String time_start, String time_end) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.roomId = roomId;
        this.roomName = roomName;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    //  private RoomResponse room;
}
