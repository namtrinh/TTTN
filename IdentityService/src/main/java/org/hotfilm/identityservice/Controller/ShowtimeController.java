package org.hotfilm.identityservice.Controller;

import org.hotfilm.identityservice.Mapper.ShowtimeMapper;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponseById;
import org.hotfilm.identityservice.Service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/showtime")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private ShowtimeMapper showtimeMapper;

    @GetMapping
    public ApiResponse<List<ShowtimeResponse>> getAllShowtimes(@RequestParam("showtime") Date dateTime) {
        return ApiResponse.<List<ShowtimeResponse>>builder()
                .status(HttpStatus.OK)
                .result(showtimeService.findAll(dateTime))
                .build();
    }

    @GetMapping("/{showtimeId}")
    public ApiResponse<ShowtimeResponseById> getShowtimeById(@PathVariable String showtimeId) {
        return ApiResponse.<ShowtimeResponseById>builder()
                .status(HttpStatus.OK)
                .result(showtimeService.findById(showtimeId))
                .build();
    }

    @PostMapping
    public ApiResponse<ShowtimeResponse> createShowtime(@RequestBody ShowtimeRequest showtimeRequest) {
        return ApiResponse.<ShowtimeResponse>builder()
                .status(HttpStatus.OK)
                .result(showtimeService.createShowtime(showtimeRequest))
                .build();
    }

    @PutMapping("/{showtimeId}")
    public ApiResponse<ShowtimeResponse> updateShowtime(
            @PathVariable String showtimeId,
            @RequestBody ShowtimeRequest showtimeRequest) {
        return ApiResponse.<ShowtimeResponse>builder()
                .status(HttpStatus.OK)
                .result(showtimeService.updateById(showtimeId, showtimeRequest))
                .build();
    }

    @PutMapping(value = "/set/{showtimeId}")
    public ApiResponse<ShowtimeResponse> setMovieToShowtime(@PathVariable String showtimeId, @RequestBody Movie movieId){
        return ApiResponse.<ShowtimeResponse>builder()
                .status(HttpStatus.OK)
                .result(showtimeService.setMovieToShowtime(showtimeId, movieId))
                .build();
    }

    @DeleteMapping("/{showtimeId}")
    public void deleteShowtime(@PathVariable String showtimeId) {
        showtimeService.deleteById(showtimeId);
    }
}
