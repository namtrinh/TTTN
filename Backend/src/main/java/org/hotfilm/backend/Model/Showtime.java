package org.hotfilm.backend.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "showtime")
public class Showtime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String showtimeId;

    private String theaterId;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime time_start;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime time_end;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Movie movie;

}
