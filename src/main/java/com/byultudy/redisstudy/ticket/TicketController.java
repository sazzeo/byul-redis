package com.byultudy.redisstudy.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/add")
    public TicketDto addTicket(TicketDto ticketDto) {
        return ticketService.create(ticketDto);
    }

}
