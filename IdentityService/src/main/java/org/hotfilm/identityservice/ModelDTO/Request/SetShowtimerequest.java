package org.hotfilm.identityservice.ModelDTO.Request;

import lombok.*;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.Model.Room.RoomType;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class SetShowtimerequest implements Serializable {

   private Movie movie;

    private Room room;
}
