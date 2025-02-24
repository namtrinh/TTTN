package org.hotfilm.identityservice.ModelDTO.Request;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class ShowtimeRequest implements Serializable {
    private String showtimeId;
    private String theaterId;

    private LocalDateTime time_start;

    private LocalDateTime time_end;

}
