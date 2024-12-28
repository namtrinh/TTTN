package org.hotfilm.identityservice.Controller;

import lombok.RequiredArgsConstructor;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.hotfilm.identityservice.ModelDTO.Response.MovieResponse;
import org.hotfilm.identityservice.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ApiResponse<List<MovieResponse>> getAll() {
        return ApiResponse.<List<MovieResponse>>builder()
                .code(200)
                .result(movieService.findAll())
                .build();
    }

    @GetMapping(value = "/findById/{movieId}")
    public ApiResponse<Movie> getById(@PathVariable String movieId) {
        return ApiResponse.<Movie>builder()
                .code(200)
                .result(movieService.findById(movieId))
                .build();
    }

    @GetMapping(value = "/findByTitle/{title}")
    public ApiResponse<Movie> findByMovieTitle(@PathVariable String title) {
        return ApiResponse.<Movie>builder()
                .code(200)
                .result(movieService.findByMovieTitle(title))
                .build();
    }

    @PostMapping
    public ApiResponse<Movie> create(@RequestBody Movie movie) {
        return ApiResponse.<Movie>builder()
                .code(200)
                .result(movieService.save(movie))
                .build();
    }

    @PutMapping(value = "/{movieId}")
    public ApiResponse<Movie> updateById(@PathVariable String movieId, @RequestBody Movie movie) {
        return ApiResponse.<Movie>builder()
                .code(200)
                .result(movieService.updateById(movieId, movie))
                .build();
    }

    @DeleteMapping(value = "/{movieId}")
    public void deleteById(@PathVariable String movieId) {
        movieService.deleteById(movieId);
    }
}
