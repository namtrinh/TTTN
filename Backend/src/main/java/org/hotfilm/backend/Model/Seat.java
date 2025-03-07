package org.hotfilm.backend.Model;

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
@Table(name = "seat")
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String seatId;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    private LocalDateTime time_selected = LocalDateTime.now();

    private String showtimeId;

    public enum SeatStatus {
        AVAILABLE, RESERVED
    }
}
