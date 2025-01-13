package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Mapper.ShowtimeMapper;
import org.hotfilm.identityservice.Model.Showtime;
import org.hotfilm.identityservice.ModelDTO.Response.ShowtimeResponse;
import org.hotfilm.identityservice.Repository.ShowtimeRepository;
import org.hotfilm.identityservice.Service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeServiceImp implements ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ShowtimeMapper showtimeMapper;

    @Override
    public List<ShowtimeResponse> findAll() {
        return showtimeRepository.findAll()
                .stream()
                .map(showtimeMapper::toShowtimeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Showtime save(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    @Override
    public Showtime findById(String showtimeId) {
        return showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime with ID " + showtimeId + " not found"));
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
    public Showtime updateById(String showtimeId, Showtime showtime) {
        showtime.setShowtimeId(showtimeId);
        return showtimeRepository.save(showtime);
    }
}
