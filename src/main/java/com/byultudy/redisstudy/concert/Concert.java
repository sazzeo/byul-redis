package com.byultudy.redisstudy.concert;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concertId")
    private Long id;

    private Long ticketQuantity;

    private Boolean hasQuantity;

    public void subtractTicketQuantity(Long ticketQuantity) {
        this.ticketQuantity = this.ticketQuantity - ticketQuantity;
        if (this.ticketQuantity <= 0) {
            this.hasQuantity = false;
        }
    }
}