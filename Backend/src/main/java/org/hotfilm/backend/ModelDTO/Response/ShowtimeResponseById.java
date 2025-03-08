package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeResponseById implements Serializable {
    private String showtimeId;

    private LocalDateTime time_start;

    private LocalDateTime time_end;


    //  private RoomResponse room;
}
