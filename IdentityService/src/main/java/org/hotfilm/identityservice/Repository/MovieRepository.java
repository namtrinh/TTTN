package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.ModelDTO.Response.MovieResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByMovieTitle(String title);
}
