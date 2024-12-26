package org.hotfilm.identityservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roomId;

    private String roomName;

    private int totalSeat;

    private String theaterId;

    private double roomPrice;

    private RoomType roomType;

    @OneToMany
    private Set<Seat> seat;

    public enum RoomType{
        STANDARD, IMAX, COUPLE, KIDS
    }
}
