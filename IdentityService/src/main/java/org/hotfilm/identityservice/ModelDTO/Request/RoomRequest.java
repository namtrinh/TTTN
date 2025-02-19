package org.hotfilm.identityservice.ModelDTO.Request;

import lombok.*;
import org.hotfilm.identityservice.Model.Room.RoomType;

import java.io.Serializable;
import java.util.Set;

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
