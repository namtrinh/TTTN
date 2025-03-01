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

    private String roomId;

    private String time_start;

    private String time_end;

    //  private RoomResponse room;
}
