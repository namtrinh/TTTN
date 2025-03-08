package org.hotfilm.backend.ModelDTO.Request;

import lombok.*;
import org.hotfilm.backend.Model.Room.RoomType;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class RoomRequest implements Serializable {
    private String roomName;
    private int totalSeat;
    private double roomPrice;
    private RoomType roomType;
}
