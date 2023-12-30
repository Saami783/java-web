package com.locar.entities;

import jakarta.persistence.*;

@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private float montantTotal;
    private boolean isPaid;
    @OneToOne(mappedBy = "facture", fetch = FetchType.LAZY)
    private Reservation reservation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(float montantTotal) {
        this.montantTotal = montantTotal;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", montantTotal=" + montantTotal +
                ", isPaid=" + isPaid +
                ", reservation=" + reservation +
                '}';
    }
}
