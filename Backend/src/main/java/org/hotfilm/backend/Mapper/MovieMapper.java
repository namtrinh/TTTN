package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.Movie;
import org.hotfilm.backend.ModelDTO.Request.MovieCreateRequest;
import org.hotfilm.backend.ModelDTO.Request.MovieRequest;
import org.hotfilm.backend.ModelDTO.Response.MovieResponse;
import org.hotfilm.backend.ModelDTO.Response.MovieResponseDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toMovie(MovieRequest movieRequest);

    Movie MovieCreateToMovie(MovieCreateRequest movieCreate);

    MovieResponse toMovieReponse(Movie movie);

    void updateMovieFromDto(MovieCreateRequest dto, @MappingTarget Movie entity);

    MovieResponseDetail toMovieResponseDetail(Movie entity);

}
