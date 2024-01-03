package com.locar.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;
    private String calendrier;
    private float prix;
    private int km;

    public Tarif() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getCalendrier() {
        return calendrier;
    }
    public void setCalendrier(String calendrier) {
        this.calendrier = calendrier;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    public int getKm() {
        return km;
    }
    public void setKm(int km) {
        this.km = km;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public String toString() {
        return "Tarif{" +
                "id=" + id +
                ", calendrier='" + calendrier + '\'' +
                ", prix=" + prix +
                ", km=" + km;
    }
}
