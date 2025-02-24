package org.hotfilm.identityservice.Controller;

import org.hotfilm.identityservice.Mapper.ShowtimeMapper;
import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
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
    public ResponseEntity<ShowtimeResponse> getShowtimeById(@PathVariable String showtimeId) {
        Showtime showtime = showtimeService.findById(showtimeId);
        return ResponseEntity.ok(showtimeMapper.toShowtimeResponse(showtime));
    }

    @PostMapping
    public ResponseEntity<ShowtimeResponse> createShowtime(@RequestBody ShowtimeRequest showtimeRequest) {
        Showtime showtime = showtimeMapper.toShowtime(showtimeRequest);
        Showtime savedShowtime = showtimeService.save(showtime);
        return ResponseEntity.ok(showtimeMapper.toShowtimeResponse(savedShowtime));
    }

    @PutMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeResponse> updateShowtime(
            @PathVariable String showtimeId,
            @RequestBody ShowtimeRequest showtimeRequest) {
        Showtime showtime = showtimeMapper.toShowtime(showtimeRequest);
        Showtime updatedShowtime = showtimeService.updateById(showtimeId, showtime);
        return ResponseEntity.ok(showtimeMapper.toShowtimeResponse(updatedShowtime));
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable String showtimeId) {
        showtimeService.deleteById(showtimeId);
        return ResponseEntity.noContent().build();
    }
}
