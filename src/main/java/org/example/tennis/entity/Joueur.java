package org.example.tennis.entity;

public class Joueur {

    private Long id;
    private String prenom;
    private String nom;
    private Character sexe;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSexe(Character sexe) {
        this.sexe = sexe;
    }

    public Long getId() {
        return id;
    }

    public Character getSexe() {
        return sexe;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " identifiant n° " + id;
    }
}
