package com.example.helpdesk.Spring_AI_Helpdesk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "helpdesk_tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String summary;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(unique = true)
    private String username;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    private Status status;

    void preSave() {
        if (Objects.isNull(this.createdOn)) {
            this.createdOn = LocalDateTime.now();
        } else {
            this.updatedOn = LocalDateTime.now();
        }
    }

}
