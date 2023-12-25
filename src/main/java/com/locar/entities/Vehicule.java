package com.locar.entities;

import java.util.ArrayList;

public class Vehicule {
    private long id;
    private String modele;
    private float caution;
    private int nbKm;
    private int nbPlace;
    private String couleur;
    private int annee;
    private String boiteVitesse;
    private String fuel;
    private int nbCheveux;
    private boolean disponible;
    private String etat;
    private String description;
    private ArrayList<Tarif> tarifs;
    private Categorie categorie;
    private ArrayList<Image> images;

    public Vehicule() {
        this.tarifs = new ArrayList<Tarif>();
        this.images = new ArrayList<>();
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public float getCaution() {
        return caution;
    }

    public void setCaution(float caution) {
        this.caution = caution;
    }

    public int getNbKm() {
        return nbKm;
    }

    public void setNbKm(int nbKm) {
        this.nbKm = nbKm;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getBoiteVitesse() {
        return boiteVitesse;
    }

    public void setBoiteVitesse(String boiteVitesse) {
        this.boiteVitesse = boiteVitesse;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getNbCheveux() {
        return nbCheveux;
    }

    public void setNbCheveux(int nbCheveux) {
        this.nbCheveux = nbCheveux;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Tarif> getTarifs() {
        return this.tarifs;
    }

    public ArrayList<Tarif> addTarifs(Tarif tarif)
    {
        if (!this.tarifs.contains(tarif)) {
            this.tarifs.add(tarif);
        }

        return this.tarifs;
    }

    public ArrayList<Tarif> removeTarifs(Tarif tarif)
    {
        this.tarifs.remove(tarif);
        return this.tarifs;
    }

    public ArrayList<Image> getImages() {
        return this.images;
    }

    public ArrayList<Image> addImages(Image image)
    {
        if (!this.images.contains(image)) {
            this.images.add(image);
        }

        return this.images;
    }

    public ArrayList<Image> removeImages(Image image)
    {
        this.images.remove(image);
        return this.images;
    }

    public Categorie getCategorie() { return categorie; }

    public void setCategorie(Categorie categorie)
    {
        this.categorie = categorie;
    }
}
