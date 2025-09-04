package org.example.tennis.entity;

import jakarta.persistence.*;

import java.util.Objects;
@NamedQueries({
        @NamedQuery(name = "given_sexe", query = "SELECT j FROM Joueur j WHERE j.sexe = ?1"),
        @NamedQuery(name = "given_nom", query = "SELECT j FROM Joueur j WHERE j.nom = ?1")
})
@Entity

public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prenom;
    private String nom;
    private Character sexe;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof  Joueur)) {
            return false;
        }
        Joueur joueur = (Joueur) o;
        return Objects.equals(id, joueur.id) && Objects.equals(nom, joueur.nom) && Objects.equals(prenom, joueur.prenom) && Objects.equals(sexe, joueur.sexe);
    }

    public int hashCode() {
        return Objects.hash(id, nom, prenom, nom, sexe);
    }

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
