package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Mapper.ShowtimeMapper;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Room;
import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Request.SetShowtimerequest;
import org.hotfilm.identityservice.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponseById;
import org.hotfilm.identityservice.Repository.MovieRepository;
import org.hotfilm.identityservice.Repository.RoomRepository;
import org.hotfilm.identityservice.Repository.ShowtimeRepository;
import org.hotfilm.identityservice.Service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeServiceImp implements ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ShowtimeMapper showtimeMapper;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<ShowtimeResponse> findAll(Date dateTime) {
        return showtimeRepository.findAllByTime(dateTime);
    }

    public ShowtimeResponse createShowtime(ShowtimeRequest showtimeRequest) {
        Showtime showtime = showtimeMapper.toShowtime(showtimeRequest);
        if (!showtimeRepository.existsShowtime(showtime.getTime_start(), showtime.getTime_end()).isEmpty()) {
            throw new RuntimeException("Showtime already exists");
        }
        showtimeRepository.save(showtime);
        ShowtimeResponse showtimeResponse = showtimeMapper.toShowtimeResponse(showtime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm a");
        showtimeResponse.setTime_start(dateTimeFormatter.format(showtime.getTime_start()));
        showtimeResponse.setTime_end(dateTimeFormatter.format(showtime.getTime_end()));
        return showtimeResponse;
    }


    @Override
    public ShowtimeResponseById findById(String showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime with ID " + showtimeId + " not found"));
        return showtimeMapper.toShowtimeResponseById(showtime);
    }

    @Override
    public boolean existsById(String showtimeId) {
        return showtimeRepository.existsById(showtimeId);
    }

    @Override
    public void deleteById(String showtimeId) {
        if (showtimeRepository.existsById(showtimeId)) {
            showtimeRepository.deleteById(showtimeId);
        } else {
            throw new RuntimeException("Showtime with ID " + showtimeId + " does not exist");
        }
    }

    @Override
    public ShowtimeResponse updateById(String showtimeId, ShowtimeRequest showtimeRequest) {
        Showtime showtime = showtimeMapper.toShowtime(showtimeRequest);
        showtime.setRoom(showtimeRequest.getRoom());

        showtime.setMovie(showtimeRequest.getMovie());


        showtime.setShowtimeId(showtimeId);
        showtimeRepository.save(showtime);
        ShowtimeResponse showtimeResponse = showtimeMapper.toShowtimeResponse(showtime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH-mm a");
        showtimeResponse.setTime_start(dateTimeFormatter.format(showtime.getTime_start()));
        showtimeResponse.setTime_end(dateTimeFormatter.format(showtime.getTime_end()));
        return showtimeResponse;
    }

    @Override
    public ShowtimeResponse setMovieToShowtime(String showtimeId, SetShowtimerequest setShowtimerequest){
        Room room = setShowtimerequest.getRoom();
        Movie movie = setShowtimerequest.getMovie();
        Optional<Showtime> showtime = showtimeRepository.findById(showtimeId);
        showtime.get().setShowtimeId(showtimeId);
        showtime.get().setMovie(movie);
        showtime.get().setRoom(room);
        return showtimeMapper.toShowtimeResponse(showtimeRepository.save(showtime.get()));
    }
}

