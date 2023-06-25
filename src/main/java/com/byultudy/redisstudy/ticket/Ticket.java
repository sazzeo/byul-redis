package com.byultudy.redisstudy.ticket;

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
public class Ticket {
    //예매후 티켓 발급함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId")
    private Long id;

    private Long customerId;

    private Long concertId;

    @Builder.Default
    private boolean vip = false;

    private LocalDateTime issueDate;

}