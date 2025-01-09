package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByMovieTitle(String title);

    @Query(value = "SELECT TOP 4 * FROM movie ORDER BY release_date DESC",
            nativeQuery = true)
    List<Movie> findMovieTop4();

    Page<Movie> findAll(Pageable page);

    @Query(value = "SELECT * FROM movie WHERE LOWER(movie_name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    Set<Movie> findByName(@Param("name") String name);
}
