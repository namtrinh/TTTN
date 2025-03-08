package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;
import org.hotfilm.backend.Model.Room.RoomType;

import java.io.Serializable;

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
