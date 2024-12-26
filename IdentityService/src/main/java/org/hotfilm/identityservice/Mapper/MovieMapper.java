package org.hotfilm.identityservice.Mapper;

import ch.qos.logback.core.model.ComponentModel;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.ModelDTO.Request.MovieRequest;
import org.hotfilm.identityservice.ModelDTO.Response.MovieResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toMovie(MovieRequest movieRequest);

    MovieResponse toMovieReponse(Movie movie);
}
