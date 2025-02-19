package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Mapper.SeatMapper;
import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.Model.Seat.SeatType;
import org.hotfilm.identityservice.ModelDTO.Response.SeatResponse;
import org.hotfilm.identityservice.Repository.SeatRepository;
import org.hotfilm.identityservice.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImp implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public List<SeatResponse> findAll() {
        return seatRepository.findAll()
                .stream()
                .map(seatMapper::toSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public Seat findById(String seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat with ID " + seatId + " not found"));
    }

    @Override
    public boolean existsById(String seatId) {
        return seatRepository.existsById(seatId);
    }

    @Override
    public void deleteById(String seatId) {
        if (seatRepository.existsById(seatId)) {
            seatRepository.deleteById(seatId);
        } else {
            throw new RuntimeException("Seat with ID " + seatId + " does not exist");
        }
    }

    @Override
    public Seat updateById(String seatId, Seat seat) {
        seat.setSeatId(seatId);
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> findBySeatType(SeatType seatType) {
        return seatRepository.findBySeatType(seatType);
    }

    @Override
    public List<Seat> findBySeatStatus(SeatStatus seatStatus) {
        return seatRepository.findBySeatStatus(seatStatus);
    }
}
