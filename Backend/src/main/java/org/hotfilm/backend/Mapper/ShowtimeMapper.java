package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.Showtime;
import org.hotfilm.backend.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.backend.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.backend.ModelDTO.Response.ShowtimeResponseById;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShowtimeMapper {
    Showtime toShowtime(ShowtimeRequest showtimeRequest);

    ShowtimeResponse toShowtimeResponse(Showtime showtime);

    ShowtimeResponseById toShowtimeResponseById(Showtime showtime);

}
