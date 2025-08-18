package org.example.tennis;

import org.example.tennis.entity.Joueur;
import org.example.tennis.repository.TournoiRepositoryImpl;

import java.util.jar.JarEntry;

public class TravauxPratiques {

    public static void main (String[] args) {

        Joueur federer = new Joueur();
        federer.setId(32L);

        Joueur murray = new Joueur();
        murray.setId(34L);

        System.out.println(federer);


    }
}
