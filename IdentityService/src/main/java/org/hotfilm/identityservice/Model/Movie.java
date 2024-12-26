package org.hotfilm.identityservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String movieId;
    private String movieName;
    private String movieTitle;
    private String movieDescription;
    private String genre;
    private String director;
    private LocalDate releaseDate;
    private int duration; // in minutes
    private double rating;
    private String posterUrl;


    @OneToMany
    private Set<Showtime> showtime;

}
