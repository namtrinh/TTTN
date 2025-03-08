package org.hotfilm.backend.ServiceImp;

import org.hotfilm.backend.Exception.AppException;
import org.hotfilm.backend.Exception.ErrorCode;
import org.hotfilm.backend.Mapper.MovieMapper;
import org.hotfilm.backend.Model.Movie;
import org.hotfilm.backend.ModelDTO.Request.MovieCreateRequest;
import org.hotfilm.backend.ModelDTO.Response.MovieResponse;
import org.hotfilm.backend.ModelDTO.Response.MovieResponseDetail;
import org.hotfilm.backend.Repository.MovieRepository;
import org.hotfilm.backend.Service.CloudinaryService;
import org.hotfilm.backend.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MovieServiceImp implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Movie> hashOperations;

    private static String HASH_MOVIE = "movie";

    public MovieServiceImp(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void TTL() {
        redisTemplate.expire(HASH_MOVIE, 10, TimeUnit.MINUTES);
    }

    @Override
    public List<MovieResponse> findTop4() {
        if (redisTemplate.hasKey(HASH_MOVIE)) {
            Map<String, Movie> movieMap = hashOperations.entries(HASH_MOVIE);
            List<Movie> movieList = movieMap.values()
                    .stream()
                    .sorted(Comparator.comparing(Movie::getReleaseDate).reversed())
                    .map(value -> (Movie) value)
                    .collect(Collectors.toList());
            return movieList.stream()
                    .map(movieMapper::toMovieReponse)
                    .collect(Collectors.toList());
        } else {
            System.out.println("get movie from database");
            List<Movie> movies = movieRepository.findMovieTop4();
            for (Movie movie : movies) {
                hashOperations.put(HASH_MOVIE, movie.getMovieId(), movie);
            }
            return movies.stream()
                    .map(movieMapper::toMovieReponse)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Page<MovieResponseDetail> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("releaseDate")));
        Page<Movie> pageMovie = movieRepository.findAll(pageable);

        List<MovieResponseDetail> movieResponseDetails = pageMovie.stream()
                .map(movieMapper::toMovieResponseDetail)
                .collect(Collectors.toList());

        return new PageImpl<>(movieResponseDetails, pageable, pageMovie.getTotalElements());
    }


    @Override
    public Set<Movie> searchMovie(String name) {
        if (name == null || name == "") {
            throw new AppException(ErrorCode.NAME_NOT_NULL);
        }
        return movieRepository.findByName(name);
    }

    @Override
    public Movie save(MovieCreateRequest entity, MultipartFile file) {
        Movie movie = movieMapper.MovieCreateToMovie(entity);
        if (file != null && !file.isEmpty()) {
            String poster = cloudinaryService.uploadFile(file);
            movie.setPosterUrl(poster);
        }
        //  movie.setMovieId(UUID.randomUUID().toString());
        redisTemplate.delete(HASH_MOVIE);
        return movieRepository.save(movie);
    }

    @Override
    public Movie findById(String string) {
        if (hashOperations.hasKey(HASH_MOVIE, string)) {
            return hashOperations.get(HASH_MOVIE, string);
        } else {
            Movie movie = movieRepository.findById(string).orElseThrow(() -> new RuntimeException("Movie not found"));
            hashOperations.put(HASH_MOVIE, movie.getMovieId(), movie);
            return movie;
        }
    }

    @Override
    public MovieResponseDetail findByMovieTitle(String string) {
        Movie movie = movieRepository.findByMovieTitle(string);
        MovieResponseDetail movieResponseDetail = movieMapper.toMovieResponseDetail(movie);
        return movieResponseDetail;
    }

    @Override
    public boolean existsById(String string) {
        return movieRepository.existsById(string);
    }

    @Override
    public void deleteById(String string) {
        if (hashOperations.hasKey(HASH_MOVIE, string)) {
            hashOperations.delete(HASH_MOVIE, string);
        }
        if (movieRepository.existsById(string)) {
            Optional<Movie> movie = movieRepository.findById(string);
            if (movie.get().getPosterUrl() != null && !movie.get().getPosterUrl().isEmpty()) {

                cloudinaryService.deleteFile(movie.get().getPosterUrl());
            }
            movieRepository.deleteById(string);
        } else {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public Movie updateById(String movieId, MovieCreateRequest movieCreateRequest, MultipartFile file) {
        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        movieMapper.updateMovieFromDto(movieCreateRequest, existingMovie);

        if (file != null && !file.isEmpty()) {
            String poster = cloudinaryService.uploadFile(file);
            if (existingMovie.getPosterUrl() != null && !existingMovie.getPosterUrl().isEmpty()) {
                cloudinaryService.deleteFile(existingMovie.getPosterUrl());
            }
            existingMovie.setPosterUrl(poster);
        }
        if (hashOperations.hasKey(HASH_MOVIE, movieId)) {
            hashOperations.put(HASH_MOVIE, movieId, existingMovie);
        }
        return movieRepository.save(existingMovie);
    }

}
