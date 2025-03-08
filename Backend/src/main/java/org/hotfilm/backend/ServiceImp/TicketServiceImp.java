package org.hotfilm.backend.ServiceImp;

import org.hotfilm.backend.Mapper.TicketMapper;
import org.hotfilm.backend.Model.Ticket;
import org.hotfilm.backend.ModelDTO.Request.TicketRequest;
import org.hotfilm.backend.ModelDTO.Response.TicketResponse;
import org.hotfilm.backend.Repository.TicketRepository;
import org.hotfilm.backend.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImp implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<TicketResponse> findAll() {
        List<Ticket> tickets =  ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::toTicketResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponse save(TicketRequest entity) {
        Ticket ticket = ticketMapper.toTicket(entity);
        return ticketMapper.toTicketResponse(ticketRepository.save(ticket));
    }

    @Override
    public TicketResponse findById(String string) {
        Ticket ticket = ticketRepository.findById(string).orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticketMapper.toTicketResponse(ticket);
    }

    @Override
    public TicketResponse updateById(String id, TicketRequest ticketRequest){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setTicketStatus(Ticket.TicketStatus.CHECK_IN);
        return ticketMapper.toTicketResponse(ticketRepository.save(ticket));
    }
}
