package org.example.tennis.services;

import org.example.tennis.entity.Match;
import org.example.tennis.repository.MatchRepositoryImpl;
import org.example.tennis.repository.ScoreRepositoryImpl;

public class MatchServices {

    private ScoreRepositoryImpl scoreRepository;
    private MatchRepositoryImpl matchRepository;

    public MatchServices() {
        this.scoreRepository = new ScoreRepositoryImpl();
        this.matchRepository = new MatchRepositoryImpl();
    }

    public void enregistrezVosMatch(Match match) {
        matchRepository.createMatch(match);
        scoreRepository.createScore(match.getScore());
    }
}
