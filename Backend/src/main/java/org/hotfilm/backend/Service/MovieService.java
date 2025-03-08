package org.hotfilm.backend.Service;

import org.hotfilm.backend.Model.Movie;
import org.hotfilm.backend.ModelDTO.Request.MovieCreateRequest;
import org.hotfilm.backend.ModelDTO.Response.MovieResponse;
import org.hotfilm.backend.ModelDTO.Response.MovieResponseDetail;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface MovieService {
    List<MovieResponse> findTop4();

    Page<MovieResponseDetail> findAll(int page, int size);

    Set<Movie> searchMovie(String name);

    Movie save(MovieCreateRequest entity, MultipartFile file);

    Movie findById(String string);

    MovieResponseDetail findByMovieTitle(String string);

    boolean existsById(String string);

    void deleteById(String string);

    Movie updateById(String movieId, MovieCreateRequest movie, MultipartFile file);
}
