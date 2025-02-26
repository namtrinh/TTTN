package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeResponseById implements Serializable {
    private String showtimeId;

    private String theaterId;

    private LocalDateTime time_start;

    private LocalDateTime time_end;

    public ShowtimeResponseById(String showtimeId, LocalDateTime time_start, LocalDateTime time_end) {
        this.showtimeId = showtimeId;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    //  private RoomResponse room;
}
