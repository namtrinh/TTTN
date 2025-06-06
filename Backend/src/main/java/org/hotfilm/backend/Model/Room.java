package org.hotfilm.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "room")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roomId;

    private String roomName;

    private int totalSeat;

    private String theaterId;

    private double roomPrice;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Seat> seat;



    public enum RoomType{
        STANDARD, IMAX, COUPLE, KID
    }
}
