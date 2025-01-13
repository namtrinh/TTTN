package org.hotfilm.identityservice.Service;

import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.ModelDTO.Response.MovieResponse;

import java.util.List;

public interface MovieService {
    List<MovieResponse> findAll();

    Movie save(Movie entity);

    Movie findById(String string);

    Movie findByMovieTitle(String string);

    boolean existsById(String string);

    void deleteById(String string);

    Movie updateById(String movieId, Movie movie);
}
