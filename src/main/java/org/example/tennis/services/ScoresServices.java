package org.example.tennis.services;

import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.*;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Match;
import org.example.tennis.entity.Score;
import org.example.tennis.repository.ScoreRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ScoresServices {

    public ScoreFullDto getScore(Long id) {

        ScoreRepositoryImpl scoreRepository = new ScoreRepositoryImpl();

        Session session = null;
        Transaction tx = null;
        Score score = null;
        Match match = null;
        ScoreFullDto scoreFullDto = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            score = scoreRepository.getById(id);

            scoreFullDto = new ScoreFullDto();
            scoreFullDto.setId(score.getId());
            scoreFullDto.setSet1(score.getSet1());
            scoreFullDto.setSet2(score.getSet2());
            scoreFullDto.setSet3(score.getSet3());
            scoreFullDto.setSet4(score.getSet4());
            scoreFullDto.setSet5(score.getSet5());

            MatchDto matchDto = new MatchDto();
            matchDto.setId(score.getMatch().getId());

            scoreFullDto.setMatchDto(matchDto);

            EpreuvesFullDto epreuvesFullDto = new EpreuvesFullDto();
            epreuvesFullDto.setId(score.getMatch().getEpreuve().getId()) ;
            epreuvesFullDto.setAnnee(score.getMatch().getEpreuve().getAnnee());
            epreuvesFullDto.setTypeEpreuve(score.getMatch().getEpreuve().getTypeEpreuve());

            TournoiDto tournoiDto = new TournoiDto();
            tournoiDto.setId(score.getMatch().getEpreuve().getTournoi().getId());
            tournoiDto.setCode(score.getMatch().getEpreuve().getTournoi().getCode());
            tournoiDto.setNom(score.getMatch().getEpreuve().getTournoi().getNom());
            epreuvesFullDto.setTournoiDto(tournoiDto);

            matchDto.setEpreuvesFullDto(epreuvesFullDto);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return scoreFullDto;
    }
}
