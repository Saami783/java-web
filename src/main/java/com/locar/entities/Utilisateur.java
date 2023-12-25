package com.locar.entities;

import jakarta.persistence.*;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roles = "ROLE_USER";
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private String password;

    @Column(name = "permis_path")
    private String permisPath;
    private int age;
    private boolean isVerified;
    private boolean isVerifiedByAdmin;

    public Utilisateur() { }

    public Utilisateur(Long id, String roles, String nom, String prenom, String email, String adresse, String codePostal, String ville, String telephone, String password, String permisPath, int age, boolean isVerified, boolean isVerifiedByAdmin) {
        this.id = id;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.telephone = telephone;
        this.password = password;
        this.permisPath = permisPath;
        this.age = age;
        this.isVerified = isVerified;
        this.isVerifiedByAdmin = isVerifiedByAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermisPath() {
        return permisPath;
    }

    public void setPermisPath(String permis_path) {
        this.permisPath = permis_path;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isVerifiedByAdmin() {
        return isVerifiedByAdmin;
    }

    public void setVerifiedByAdmin(boolean verifiedByAdmin) {
        isVerifiedByAdmin = verifiedByAdmin;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", roles='" + roles + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                ", permis_path='" + permisPath + '\'' +
                ", age=" + age +
                ", isVerified=" + isVerified +
                ", isVerifiedByAdmin=" + isVerifiedByAdmin +
                '}';
    }
}
