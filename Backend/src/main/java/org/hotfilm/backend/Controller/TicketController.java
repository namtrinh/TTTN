package org.hotfilm.backend.Controller;

import com.cloudinary.Api;
import org.hotfilm.backend.ModelDTO.Request.TicketRequest;
import org.hotfilm.backend.ModelDTO.Response.ApiResponse;
import org.hotfilm.backend.ModelDTO.Response.TicketResponse;
import org.hotfilm.backend.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping(value = "ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ApiResponse<List<TicketResponse>> getAll() {
        return ApiResponse.<List<TicketResponse>>builder()
                .status(HttpStatus.OK)
                .result(ticketService.findAll())
                .build();
    }

    @PostMapping
    public ApiResponse<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest) {
        return ApiResponse.<TicketResponse>builder()
                .status(HttpStatus.OK)
                .result(ticketService.save(ticketRequest))
                .build();
    }

    @PutMapping(value = "/{ticketId}")
    public ApiResponse<TicketResponse> updateById(@PathVariable String ticketId, @RequestBody TicketRequest ticketRequest) {
        return ApiResponse.<TicketResponse>builder()
                .status(HttpStatus.OK)
                .result(ticketService.updateById(ticketId, ticketRequest))
                .build();
    }
}
