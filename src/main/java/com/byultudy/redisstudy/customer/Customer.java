package com.byultudy.redisstudy.customer;

import jakarta.persistence.*;


@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Long id;

    private String name;

}