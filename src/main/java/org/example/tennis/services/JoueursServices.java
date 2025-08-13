package org.example.tennis.services;

import org.example.tennis.entity.Joueur;
import org.example.tennis.repository.JoueurRepositoryImpl;

public class JoueursServices {

    private JoueurRepositoryImpl joueurRepository;

    public JoueursServices() {

        this.joueurRepository = new JoueurRepositoryImpl();
    }

    public void createPlayer(Joueur joueur) {

        joueurRepository.createPlayer(joueur);
    }

    public Joueur getPlayer(Long id) {

        return joueurRepository.getById(id);
    }
}
