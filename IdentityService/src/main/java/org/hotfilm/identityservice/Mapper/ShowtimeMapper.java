package org.hotfilm.identityservice.Mapper;

import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponseById;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShowtimeMapper {
    Showtime toShowtime(ShowtimeRequest showtimeRequest);

    ShowtimeResponse toShowtimeResponse(Showtime showtime);

    ShowtimeResponseById toShowtimeResponseById(Showtime showtime);

}
