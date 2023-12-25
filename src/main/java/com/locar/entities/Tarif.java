package com.locar.entities;

import jakarta.persistence.*;

@Entity
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String calendrier;
    private float prix;
    private int km;
    @ManyToOne
    private Vehicule vehicule;

    public Tarif() { }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCalendrier() { return calendrier; }
    public void setCalendrier(String calendrier) { this.calendrier = calendrier; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }
    public int getKm() { return km; }
    public void setKm(int km) { this.km = km; }
}
