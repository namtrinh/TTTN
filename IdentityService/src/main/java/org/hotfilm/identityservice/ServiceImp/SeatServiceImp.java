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
    public List<SeatResponse> findAll(String showtimeId) {
        return seatRepository.findAllByShowtimeId(showtimeId)
                .stream()
                .map(seatMapper::toSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Seat> createSeat(int row, int col, String showtimeId) {
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
                seat.setShowtimeId(showtimeId);
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
    public void deleteByShowtimeId(String showtimeId) {
            seatRepository.deleteAllByShowtimeId(showtimeId);
    }

    @Override
    public Seat updateById(String seatId, Seat seat) {
        seat.setSeatId(seatId);
        return seatRepository.save(seat);
    }
}
