package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.Ticket;
import org.hotfilm.backend.ModelDTO.Request.TicketRequest;
import org.hotfilm.backend.ModelDTO.Response.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "showtime", ignore = true)
    Ticket toTicket(TicketRequest ticketRequest);

    TicketResponse toTicketResponse(Ticket ticket);
}
