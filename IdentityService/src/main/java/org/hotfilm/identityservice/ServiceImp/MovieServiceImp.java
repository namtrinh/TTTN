package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Exception.AppException;
import org.hotfilm.identityservice.Exception.ErrorCode;
import org.hotfilm.identityservice.Mapper.MovieMapper;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.ModelDTO.Response.MovieResponse;
import org.hotfilm.identityservice.Repository.MovieRepository;
import org.hotfilm.identityservice.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MovieServiceImp implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapper movieMapper;

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
    public List<MovieResponse> findAll() {
        if (redisTemplate.hasKey(HASH_MOVIE)) {
            Map<String, Movie> movieMap = hashOperations.entries(HASH_MOVIE);
            List<Movie> movieList = movieMap.values()
                    .stream()
                    .map(value -> (Movie) value)
                    .collect(Collectors.toList());
            return movieList.stream()
                    .map(movieMapper::toMovieReponse)
                    .collect(Collectors.toList());
        } else {
            System.out.println("get movie from database");
            List<Movie> movies = movieRepository.findAll();
            for (Movie movie : movies) {
                hashOperations.put(HASH_MOVIE, movie.getMovieId(), movie);
            }
            return movies.stream()
                    .map(movieMapper::toMovieReponse)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Movie save(Movie entity) {
        return movieRepository.save(entity);
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
    public Movie findByMovieTitle(String string) {
          return movieRepository.findByMovieTitle(string);

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
            movieRepository.deleteById(string);
        }else{
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public Movie updateById(String movieId, Movie movie) {
        if (hashOperations.hasKey(HASH_MOVIE, movieId)) {
            hashOperations.put(HASH_MOVIE, movieId, movie);
        }
        movie.setMovieId(movieId);
        return movieRepository.save(movie);
    }

}
