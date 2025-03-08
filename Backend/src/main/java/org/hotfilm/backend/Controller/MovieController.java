package org.hotfilm.backend.Controller;

import org.hotfilm.backend.Model.Movie;
import org.hotfilm.backend.ModelDTO.Request.MovieCreateRequest;
import org.hotfilm.backend.ModelDTO.Response.ApiResponse;
import org.hotfilm.backend.ModelDTO.Response.MovieResponse;
import org.hotfilm.backend.ModelDTO.Response.MovieResponseDetail;
import org.hotfilm.backend.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ApiResponse<List<MovieResponse>> findTop4() {
        return ApiResponse.<List<MovieResponse>>builder()
                .status(HttpStatus.OK)
                .result(movieService.findTop4())
                .build();
    }

    @GetMapping(value = "manage/movie")
    public ApiResponse<Page<MovieResponseDetail>> findAll(@RequestParam int page, @RequestParam int size) {
        return ApiResponse.<Page<MovieResponseDetail>>builder()
                .status(HttpStatus.OK)
                .result(movieService.findAll(page, size))
                .build();
    }

    @GetMapping(value = "search")
    public ApiResponse<Set<Movie>> searchMovie(@RequestParam String name) {
        return ApiResponse.<Set<Movie>>builder()
                .status(HttpStatus.OK)
                .result(movieService.searchMovie(name))
                .build();
    }

    @GetMapping(value = "/findById/{movieId}")
    public ApiResponse<Movie> getById(@PathVariable String movieId) {
        return ApiResponse.<Movie>builder()
                .status(HttpStatus.OK)
                .result(movieService.findById(movieId))
                .build();
    }

    @GetMapping(value = "/findByTitle/{title}")
    public ApiResponse<MovieResponseDetail> findByMovieTitle(@PathVariable String title) {
        return ApiResponse.<MovieResponseDetail>builder()
                .status(HttpStatus.OK)
                .result(movieService.findByMovieTitle(title))
                .build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Movie> create(@ModelAttribute MovieCreateRequest movie,
                                     @RequestParam(value = "posterUrl", required = false) MultipartFile posterUrl) {
        return ApiResponse.<Movie>builder()
                .status(HttpStatus.OK)
                .result(movieService.save(movie, posterUrl))
                .build();
    }

    @PutMapping(value = "/{movieId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Movie> updateById(@PathVariable String movieId,
                                         @ModelAttribute MovieCreateRequest movie,
                                         @RequestParam(value = "posterUrl", required = false) MultipartFile file) {
        return ApiResponse.<Movie>builder()
                .status(HttpStatus.OK)
                .result(movieService.updateById(movieId, movie, file))
                .build();
    }

    @DeleteMapping(value = "/{movieId}")
    public void deleteById(@PathVariable String movieId) {
        movieService.deleteById(movieId);
    }
}
