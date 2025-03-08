package org.hotfilm.backend.ModelDTO.Request;

import lombok.*;
import org.hotfilm.backend.Model.Movie;
import org.hotfilm.backend.Model.Room;

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
