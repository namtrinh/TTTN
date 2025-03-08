package org.hotfilm.backend.ModelDTO.Response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hotfilm.backend.Model.Order;
import org.hotfilm.backend.Model.Ticket;
import org.springframework.cglib.core.internal.LoadingCache;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TicketResponse {
    private String ticketId;
    private String movieId;
    private String movieName;
    private LocalDateTime showtime;
    private String seatNumber;
    private String seatPrice;

    private Ticket.TicketStatus ticketStatus;

}
