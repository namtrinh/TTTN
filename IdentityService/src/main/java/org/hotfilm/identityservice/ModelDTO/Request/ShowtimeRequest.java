package org.hotfilm.identityservice.ModelDTO.Request;

import lombok.*;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Room;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeRequest implements Serializable {

    private LocalDateTime time_start;

    private LocalDateTime time_end;

    private String movieId;

    private String roomId;
}
