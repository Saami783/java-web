package com.locar.entities;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Avis() { }

    @Override
    public String toString() {
        return "Avis{" +
                "id=" + id +
                ", reservation=" + reservation +
                '}';
    }
}
