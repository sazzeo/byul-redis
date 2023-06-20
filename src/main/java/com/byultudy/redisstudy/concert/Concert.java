package com.byultudy.redisstudy.concert;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime targetDateTime;

    public void subtractTicketQuantity(Long ticketQuantity) {
        this.ticketQuantity -= ticketQuantity;
    }
}