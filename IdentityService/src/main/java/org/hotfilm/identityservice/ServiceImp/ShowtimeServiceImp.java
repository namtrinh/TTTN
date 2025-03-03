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
import org.hotfilm.identityservice.Repository.SeatRepository;
import org.hotfilm.identityservice.Repository.ShowtimeRepository;
import org.hotfilm.identityservice.Service.SeatService;
import org.hotfilm.identityservice.Service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    @Autowired
    private SeatService seatService;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<ShowtimeResponse> findAll(Date dateTime, String roomId, String movieId) {
        List<ShowtimeResponse> showtimeResponses = new ArrayList<>();
        if (movieId == null || movieId.isEmpty()) {
            showtimeResponses = showtimeRepository.findAllByTime(dateTime, roomId);
        } else {
            showtimeResponses = showtimeRepository.findAllByTimeAndMovie(dateTime, movieId);
        }
        return showtimeResponses;
    }

    public ShowtimeResponse createShowtime(ShowtimeRequest showtimeRequest) {
        Showtime showtime = showtimeMapper.toShowtime(showtimeRequest);
        if (showtimeRequest.getRoomId() != null) {
            Optional<Room> room = roomRepository.findById(showtimeRequest.getRoomId());
            showtime.setRoom(room.get());
        }
        //    if (!showtimeRepository.existsShowtime(showtime.getTime_start(), showtime.getTime_end()).isEmpty()) {
        //      throw new RuntimeException("Showtime already exists");
        // }

        showtimeRepository.save(showtime);
        if (showtime.getRoom() != null) {
            switch (showtime.getRoom().getRoomType().toString()) {
                case "STANDARD":
                    seatService.createSeat(8, 8, showtime.getShowtimeId());
                    break;
                case "IMAX":
                    seatService.createSeat(10, 10, showtime.getShowtimeId());
                    break;
                case "COUPLE":
                    seatService.createSeat(6, 7, showtime.getShowtimeId());
                    break;
            }
        }
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
    @Transactional
    public ShowtimeResponse updateById(String showtimeId, ShowtimeRequest showtimeRequest) {
        Optional<Showtime> showtime = showtimeRepository.findById(showtimeId);
        if (showtimeRequest.getRoomId() != null) {
            Optional<Room> room = roomRepository.findById(showtimeRequest.getRoomId());

            ////////////////////
            if (room.isPresent()) {
                switch (room.get().getRoomType().toString()) {
                    case "STANDARD":
                        seatService.deleteByShowtimeId(showtimeId);
                        seatService.createSeat(8, 8, showtimeId);
                        break;
                    case "IMAX":
                        seatService.deleteByShowtimeId(showtimeId);
                        seatService.createSeat(10, 10, showtimeId);
                        break;
                    case "COUPLE":
                        seatService.deleteByShowtimeId(showtimeId);
                        seatService.createSeat(6, 7, showtimeId);
                        break;
                }
                showtime.get().setRoom(room.get());
            }
            ////////////////////
        }
        if (showtimeRequest.getMovieId() != null) {
            Optional<Movie> movie = movieRepository.findById(showtimeRequest.getMovieId());
            if (movie.isPresent()) {
                showtime.get().setMovie(movie.get());
            }
        }
        if (showtimeRequest.getTime_start() != null) {
            showtime.get().setTime_start(showtimeRequest.getTime_start());
        }
        if (showtimeRequest.getTime_end() != null) {
            showtime.get().setTime_end(showtimeRequest.getTime_end());
        }
        showtimeRepository.save(showtime.get());
        ShowtimeResponse showtimeResponse = showtimeMapper.toShowtimeResponse(showtime.get());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH-mm a");
        showtimeResponse.setTime_start(dateTimeFormatter.format(showtime.get().getTime_start()));
        showtimeResponse.setTime_end(dateTimeFormatter.format(showtime.get().getTime_end()));

        return showtimeResponse;
    }
}

