package org.hotfilm.backend.ModelDTO.Request;

import lombok.*;

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
