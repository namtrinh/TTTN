package org.hotfilm.identityservice.Controller;

import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Request.ShowtimeRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.identityservice.Service.ShowtimeService;
import org.hotfilm.identityservice.Mapper.ShowtimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private ShowtimeMapper showtimeMapper;

    @GetMapping
    public ResponseEntity<List<ShowtimeResponse>> getAllShowtimes() {
        List<ShowtimeResponse> showtimes = showtimeService.findAll();
        return ResponseEntity.ok(showtimes);
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
