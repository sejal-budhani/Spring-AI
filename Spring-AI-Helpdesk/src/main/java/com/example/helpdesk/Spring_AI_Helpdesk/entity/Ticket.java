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

    private String category;

    @Column(length = 1000)
    private String description;

    @Column(unique = true)
    private String email;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    @PreUpdate
    void preSave() {
        if (Objects.isNull(this.createdOn)) {
            this.createdOn = LocalDateTime.now();
        } else {
            this.updatedOn = LocalDateTime.now();
        }
    }

}
