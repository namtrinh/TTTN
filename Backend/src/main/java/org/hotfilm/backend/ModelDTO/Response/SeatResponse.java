package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;
import org.hotfilm.backend.Model.Seat.SeatStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class SeatResponse implements Serializable {
    private String seatId;
    private String seatNumber;
    private SeatStatus seatStatus;
    private String theaterId;
}
