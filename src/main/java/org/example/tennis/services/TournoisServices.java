package org.example.tennis.services;

import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Tournoi;
import org.example.tennis.repository.TournoiRepositoryImpl;

public class TournoisServices {

    public Tournoi getPlayer(Long id) {

        TournoiRepositoryImpl tournoiRepository = new TournoiRepositoryImpl();

        return tournoiRepository.getById(id);
    }

    public Tournoi getTournois(Long id) {
        TournoiRepositoryImpl tournoiRepository = new TournoiRepositoryImpl();

        return tournoiRepository.getById(id);
    }

    public Tournoi createTournoi(Tournoi tournoi) {
        TournoiRepositoryImpl tournoiRepository = new TournoiRepositoryImpl();

        return tournoiRepository.createTournoi(tournoi);
    }
}




