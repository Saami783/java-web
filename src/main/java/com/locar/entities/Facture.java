package com.locar.entities;

import jakarta.persistence.*;

@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "facture", fetch = FetchType.LAZY)
    private Reservation reservation;

    public Facture() { }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", reservation=" + reservation +
                '}';
    }
}
