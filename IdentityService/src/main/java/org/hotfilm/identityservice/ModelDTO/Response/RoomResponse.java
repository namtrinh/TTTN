package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;
import org.hotfilm.identityservice.Model.Room.RoomType;
import org.hotfilm.identityservice.Model.Seat;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RoomResponse implements Serializable {
    private String roomId;
    private String roomName;
    private int totalSeat;
    private double roomPrice;
    private RoomType roomType;
}
