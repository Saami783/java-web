package com.locar.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDebutReservation;
    private Date dateFinReservation;
    private int nbJourReserve;
    private float prix;
    private String paymentToken;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "facture_id", referencedColumnName = "id")
    private Facture facture;
    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
    private Date createdAt;

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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String payement_token) {
        this.paymentToken = payement_token;
    }

    public Date getCreatedAt() {
        return createdAt;
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
                ", facture=" + facture +
                '}';
    }
}
