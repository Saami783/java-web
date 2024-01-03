package com.locar.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float montantTotal;
    private Date createdAt;


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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", montantTotal=" + montantTotal +
                '}';
    }
}
