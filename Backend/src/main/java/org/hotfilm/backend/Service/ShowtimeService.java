package org.hotfilm.backend.Service;

import org.hotfilm.backend.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.backend.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.backend.ModelDTO.Response.ShowtimeResponseById;

import java.sql.Date;
import java.util.List;

public interface ShowtimeService {
    List<ShowtimeResponse> findAll(Date dateTime, String roomId, String movieId);

    ShowtimeResponse createShowtime(ShowtimeRequest showtimeRequest);

    ShowtimeResponseById findById(String showtimeId);

    boolean existsById(String showtimeId);

    void deleteById(String showtimeId);

    ShowtimeResponse updateById(String showtimeId, ShowtimeRequest showtimeRequest);
}
