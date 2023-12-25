package com.locar.entities;

import jakarta.persistence.*;

@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Facture() { }
}
