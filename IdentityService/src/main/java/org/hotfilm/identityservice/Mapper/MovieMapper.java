package org.hotfilm.identityservice.Mapper;

import ch.qos.logback.core.model.ComponentModel;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.ModelDTO.Request.MovieCreateRequest;
import org.hotfilm.identityservice.ModelDTO.Request.MovieRequest;
import org.hotfilm.identityservice.ModelDTO.Response.MovieResponse;
import org.hotfilm.identityservice.ModelDTO.Response.MovieResponseDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toMovie(MovieRequest movieRequest);

    Movie MovieCreateToMovie(MovieCreateRequest movieCreate);

    MovieResponse toMovieReponse(Movie movie);

    void updateMovieFromDto(MovieCreateRequest dto, @MappingTarget Movie entity);

    MovieResponseDetail toMovieResponseDetail(Movie entity);

}
