package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Mapper.SeatMapper;
import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.Model.Seat.SeatStatus;
import org.hotfilm.identityservice.ModelDTO.Response.SeatResponse;
import org.hotfilm.identityservice.Repository.SeatRepository;
import org.hotfilm.identityservice.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public Set<Seat> createSeat(int row, int col) {
        Set<Seat> listSeat = new HashSet<>();
        char[] keys = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'};
        if (row > 10 && col > 10) {
            throw new RuntimeException("Row must be between 0 and 10");
        }
        for (int i = 0; i < row; i++) {
            for (int j = 1; j <= col; j++) {
                System.out.println(keys[i] + "" + j);
                Seat seat = new Seat();
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatNumber(keys[i]+""+j);
           //     seat.setSeatId(UUID.randomUUID().toString());
                listSeat.add(seat);
                seatRepository.save(seat);
            }
        }
        return listSeat;
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
    public List<Seat> findBySeatStatus(SeatStatus seatStatus) {
        return seatRepository.findBySeatStatus(seatStatus);
    }
}
