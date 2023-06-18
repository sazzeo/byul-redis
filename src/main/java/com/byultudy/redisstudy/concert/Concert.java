package com.byultudy.redisstudy.concert;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convertId")
    private Long id;

    private Long ticketQuantity;

    private LocalDateTime targetDateTime;

}