package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeResponse implements Serializable {
    private String showtimeId;

    private String time_start;

    private String time_end;

    private String roomId;

    private String movieId;

    public ShowtimeResponse(String showtimeId, String time_start, String time_end, String roomId) {
        this.showtimeId = showtimeId;
        this.time_start = time_start;
        this.time_end = time_end;
        this.roomId = roomId;
    }

    //  private RoomResponse room;
}
