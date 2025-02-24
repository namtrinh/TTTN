package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;
import org.hotfilm.identityservice.Model.Room;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeResponse implements Serializable {
    private String showtimeId;

    private String theaterId;

    private String time_start;

    private LocalDateTime time_end;

    public ShowtimeResponse(String showtimeId, String time_start) {
        this.showtimeId = showtimeId;
        this.time_start = time_start;
    }

    //  private RoomResponse room;
}
