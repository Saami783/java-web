package com.locar.entities;

import jakarta.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;
    @ManyToOne
    private Vehicule vehicule;

    public Image() { }
    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", vehicule=" + vehicule +
                '}';
    }
}
