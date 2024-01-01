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
    private Date createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "facture_id", referencedColumnName = "id")
    private Facture facture;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Avis> avisList;
    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

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

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public List<Avis> getAvisList() {
        return avisList;
    }

    public void setAvisList(List<Avis> avisList) {
        this.avisList = avisList;
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
                ", avis=" + avisList +

                '}';
    }
}
