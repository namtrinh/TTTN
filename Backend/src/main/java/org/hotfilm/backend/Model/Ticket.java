package org.hotfilm.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketId;
    private String movieId;
    private String movieName;
    private String showtime;
    private String seatNumber;
    private String seatPrice;

    private TicketStatus ticketStatus;

    @OneToOne
    private Order order;

    public enum TicketStatus{
        CHECK_IN, NOT_CHECK_IN
    }
}
