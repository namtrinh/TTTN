package org.hotfilm.identityservice.ModelDTO.Request;


import lombok.*;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class SeatRequest {
    private String seatId;
    private String seatNumber;
    private SeatStatus seatStatus;
    private LocalDateTime time_selected;
}
