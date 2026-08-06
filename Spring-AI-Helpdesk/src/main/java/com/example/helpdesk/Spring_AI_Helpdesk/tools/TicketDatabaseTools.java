package com.example.helpdesk.Spring_AI_Helpdesk.tools;

import com.example.helpdesk.Spring_AI_Helpdesk.entity.Priority;
import com.example.helpdesk.Spring_AI_Helpdesk.entity.Status;
import com.example.helpdesk.Spring_AI_Helpdesk.entity.Ticket;
import com.example.helpdesk.Spring_AI_Helpdesk.repository.TicketRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketDatabaseTools {

    public final TicketRepository ticketRepository;

    @Tool(description = "Create a new helpdesk ticket in the database. Use this when a user wants to report a new issue and no existing ticket matches.")
    public Ticket createTicket(
            @ToolParam(description = "Short summary of the issue") String summary,
            @ToolParam(description = "Detailed description of the issue") String description,
            @ToolParam(description = "Priority level: LOW, MEDIUM, HIGH, or URGENT") String priority,
            @ToolParam(description = "Category of the issue, e.g. hardware, software, network, account") String category,
            @ToolParam(description = "Email address of the user reporting the issue") String email) {

        Ticket ticket = new Ticket();
        ticket.setSummary(summary);
        ticket.setDescription(description);
        ticket.setPriority(Priority.valueOf(priority.toUpperCase()));
        ticket.setCategory(category);
        ticket.setEmail(email);
        ticket.setStatus(Status.OPEN);
        ticket.setCreatedOn(LocalDateTime.now());

        return this.ticketRepository.save(ticket);
    }

    @Tool(description = "Get an existing ticket from the database by the user's email address")
    public Optional<Ticket> getTicketByUsername(@ToolParam(description = "Email address of the user whose ticket to look up") String email) {
        return this.ticketRepository.findByEmail(email);
    }

    @Tool(description = "Update an existing ticket in the database with new details")
    public Ticket updateTicket(
            @ToolParam(description = "The ticket ID to update") Long id,
            @ToolParam(description = "Updated summary of the issue") String summary,
            @ToolParam(description = "Updated description of the issue") String description,
            @ToolParam(description = "Updated priority: LOW, MEDIUM, HIGH, or URGENT") String priority,
            @ToolParam(description = "Updated status: OPEN, CLOSED, or RESOLVED") String status) {

        Ticket ticket = this.ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        ticket.setSummary(summary);
        ticket.setDescription(description);
        ticket.setPriority(Priority.valueOf(priority.toUpperCase()));
        ticket.setStatus(Status.valueOf(status.toUpperCase()));
        ticket.setUpdatedOn(LocalDateTime.now());

        return this.ticketRepository.save(ticket);
    }

    @Tool(description = "Get the current date and time")
    public String getCurrentDateTime() {
        return LocalDateTime.now().toString();
    }
}
