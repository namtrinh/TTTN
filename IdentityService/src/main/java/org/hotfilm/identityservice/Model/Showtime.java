package org.hotfilm.identityservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "showtime")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String showtimeId;

    private String theaterId;

    private LocalDateTime showtime;

    private LocalDateTime time_create = LocalDateTime.now();

    private LocalDateTime time_update;

    public void setShowtime(LocalDateTime showtime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm" );
        this.showtime = LocalDateTime.parse(showtime.format(formatter));
    }
}
