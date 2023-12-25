package com.locar.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDebutReservation;
    private Date dateFinReservation;
    private int nbJourReserve;
    private float prix;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Facture facture;
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Avis avis;

    public Reservation() { }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDebutReservation() {
        return dateDebutReservation;
    }

    public void setDateDebutReservation(Date dateDebutReservation) {
        this.dateDebutReservation = dateDebutReservation;
    }

    public Date getDateFinReservation() {
        return dateFinReservation;
    }

    public void setDateFinReservation(Date dateFinReservation) {
        this.dateFinReservation = dateFinReservation;
    }

    public int getNbJourReserve() {
        return nbJourReserve;
    }

    public void setNbJourReserve(int nbJourReserve) {
        this.nbJourReserve = nbJourReserve;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateDebutReservation=" + dateDebutReservation +
                ", dateFinReservation=" + dateFinReservation +
                ", nbJourReserve=" + nbJourReserve +
                ", prix=" + prix +
                ", createdAt=" + createdAt +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
