package com.example.helpdesk.Spring_AI_Helpdesk.service;

import com.example.helpdesk.Spring_AI_Helpdesk.entity.Ticket;
import com.example.helpdesk.Spring_AI_Helpdesk.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private TicketRepository ticketRepository = null;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
//    Create ticket logic
    @Transactional
    public Ticket createTicket(Ticket ticket) {
        ticket.setId(null);
        return this.ticketRepository.save(ticket);
    }

//    Update ticket logic
    @Transactional
    public Ticket updateTicket(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

//    Get ticket logic
    public Ticket getTicket(Long ticketId) {
        return this.ticketRepository.findById(ticketId).orElse(null);
    }

//    Get ticket by username
    public Ticket getTicketByUsername(String email) {
        return this.ticketRepository.findByEmail(email).orElse(null);
    }
}
