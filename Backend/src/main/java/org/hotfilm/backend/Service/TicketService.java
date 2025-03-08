package org.hotfilm.backend.Service;

import org.hotfilm.backend.ModelDTO.Request.TicketRequest;
import org.hotfilm.backend.ModelDTO.Response.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> findAll();

    TicketResponse save(TicketRequest entity);

    TicketResponse findById(String string);

    TicketResponse updateById(String id, TicketRequest ticketRequest);
}
