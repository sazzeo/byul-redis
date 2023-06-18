package com.byultudy.redisstudy.ticket;

import jakarta.persistence.*;


@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId")
    private Long id;

    private Long customerNo;

    private Long convertNo;

}