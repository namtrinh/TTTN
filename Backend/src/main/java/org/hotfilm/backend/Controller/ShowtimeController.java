package org.hotfilm.backend.Controller;

import org.hotfilm.backend.Mapper.ShowtimeMapper;
import org.hotfilm.backend.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.backend.ModelDTO.Response.ApiResponse;
import org.hotfilm.backend.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.backend.ModelDTO.Response.ShowtimeResponseById;
import org.hotfilm.backend.Service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<List<ShowtimeResponse>> getAllShowtimes(@RequestParam("showtime") Date dateTime,
                                                               @RequestParam("roomId") String roomId,
                                                               @RequestParam("movieId") String movieId) {
        return ApiResponse.<List<ShowtimeResponse>>builder()
                .status(HttpStatus.OK)
                .result(showtimeService.findAll(dateTime, roomId, movieId))
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

    @DeleteMapping("/{showtimeId}")
    public void deleteShowtime(@PathVariable String showtimeId) {
        showtimeService.deleteById(showtimeId);
    }
}
