package org.hotfilm.identityservice.Controller;

import org.hotfilm.identityservice.Mapper.SeatMapper;
import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.Model.Seat.SeatType;
import org.hotfilm.identityservice.ModelDTO.Request.SeatRequest;
import org.hotfilm.identityservice.ModelDTO.Response.SeatResponse;
import org.hotfilm.identityservice.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatMapper seatMapper;

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats() {
        List<SeatResponse> seats = seatService.findAll();
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<SeatResponse> getSeatById(@PathVariable String seatId) {
        Seat seat = seatService.findById(seatId);
        return ResponseEntity.ok(seatMapper.toSeatResponse(seat));
    }

    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@RequestBody SeatRequest seatRequest) {
        Seat seat = seatMapper.toSeat(seatRequest);
        Seat savedSeat = seatService.save(seat);
        return ResponseEntity.ok(seatMapper.toSeatResponse(savedSeat));
    }

    @PutMapping("/{seatId}")
    public ResponseEntity<SeatResponse> updateSeat(
            @PathVariable String seatId,
            @RequestBody SeatRequest seatRequest) {
        Seat seat = seatMapper.toSeat(seatRequest);
        Seat updatedSeat = seatService.updateById(seatId, seat);
        return ResponseEntity.ok(seatMapper.toSeatResponse(updatedSeat));
    }

    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeat(@PathVariable String seatId) {
        seatService.deleteById(seatId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{seatType}")
    public ResponseEntity<List<SeatResponse>> getSeatsByType(@PathVariable SeatType seatType) {
        List<Seat> seats = seatService.findBySeatType(seatType);
        return ResponseEntity.ok(seats.stream()
                .map(seatMapper::toSeatResponse)
                .toList());
    }

    @GetMapping("/status/{seatStatus}")
    public ResponseEntity<List<SeatResponse>> getSeatsByStatus(@PathVariable SeatStatus seatStatus) {
        List<Seat> seats = seatService.findBySeatStatus(seatStatus);
        return ResponseEntity.ok(seats.stream()
                .map(seatMapper::toSeatResponse)
                .toList());
    }
}
