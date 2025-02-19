package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;

import java.util.List;

public interface ShowtimeService {
    List<ShowtimeResponse> findAll();

    Showtime save(Showtime showtime);

    Showtime findById(String showtimeId);

    boolean existsById(String showtimeId);

    void deleteById(String showtimeId);

    Showtime updateById(String showtimeId, Showtime showtime);
}
