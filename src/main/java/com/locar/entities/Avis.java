package com.locar.entities;

import jakarta.persistence.*;

@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;



    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    private String commentaire;
    private int note;

    public Avis() { }



    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "id=" + id +
                ", reservation=" + reservation +
                ", commentaire='" + commentaire + '\'' +
                ", note=" + note +
                '}';
    }
}
