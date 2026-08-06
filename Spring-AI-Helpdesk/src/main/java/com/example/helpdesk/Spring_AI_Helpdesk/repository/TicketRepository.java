package com.example.helpdesk.Spring_AI_Helpdesk.repository;

import com.example.helpdesk.Spring_AI_Helpdesk.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

//    Optional<Ticket> findByTicketId(Long id);

    Optional<Ticket> findByEmail(String email);
}
