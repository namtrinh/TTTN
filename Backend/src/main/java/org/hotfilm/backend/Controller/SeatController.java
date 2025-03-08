package org.hotfilm.backend.Controller;

import org.hotfilm.backend.Mapper.SeatMapper;
import org.hotfilm.backend.Model.Seat;

import org.hotfilm.backend.ModelDTO.Request.SeatRequest;
import org.hotfilm.backend.ModelDTO.Response.ApiResponse;
import org.hotfilm.backend.ModelDTO.Response.SeatResponse;
import org.hotfilm.backend.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatMapper seatMapper;

    @GetMapping
    public ApiResponse<List<SeatResponse>> getAllSeats(@RequestParam("showtimeId") String showtimeId) {
        List<SeatResponse> seats = seatService.findAll(showtimeId);
        return ApiResponse.<List<SeatResponse>>builder()
                .status(HttpStatus.OK)
                .result(seats)
                .build();
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<SeatResponse> getSeatById(@PathVariable String seatId) {
        Seat seat = seatService.findById(seatId);
        return ResponseEntity.ok(seatMapper.toSeatResponse(seat));
    }

    @PutMapping("/{seatId}")
    public ApiResponse<SeatResponse> updateSeat(
            @PathVariable String seatId,
            @RequestBody SeatRequest seatRequest) {
        return ApiResponse.<SeatResponse>builder()
                .status(HttpStatus.OK)
                .result(seatService.updateById(seatId, seatRequest))
                .build();
    }
}
