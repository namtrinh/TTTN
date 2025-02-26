package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponseById;

import java.sql.Date;
import java.util.List;

public interface ShowtimeService {
    List<ShowtimeResponse> findAll(Date dateTime);

    ShowtimeResponse createShowtime(ShowtimeRequest showtimeRequest);

    ShowtimeResponseById findById(String showtimeId);

    boolean existsById(String showtimeId);

    void deleteById(String showtimeId);

    ShowtimeResponse updateById(String showtimeId, ShowtimeRequest showtimeRequest);
}
