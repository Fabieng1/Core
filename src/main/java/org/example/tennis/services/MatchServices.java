package org.example.tennis.services;

import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.*;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Match;
import org.example.tennis.entity.Score;
import org.example.tennis.repository.EpreuvesRepositoryImpl;
import org.example.tennis.repository.JoueurRepositoryImpl;
import org.example.tennis.repository.MatchRepositoryImpl;
import org.example.tennis.repository.ScoreRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MatchServices {

    private ScoreRepositoryImpl scoreRepository;
    private MatchRepositoryImpl matchRepository;
    private EpreuvesRepositoryImpl epreuvesRepository;
    private JoueurRepositoryImpl joueurRepository;

    public MatchServices() {
        this.scoreRepository = new ScoreRepositoryImpl();
        this.matchRepository = new MatchRepositoryImpl();
        this.epreuvesRepository = new EpreuvesRepositoryImpl();
        this.joueurRepository = new JoueurRepositoryImpl();
    }

    public void enregistrezVosMatch(Match match) {
        matchRepository.createMatch(match);
        scoreRepository.createScore(match.getScore());
    }

    public void createMatch(MatchDto matchDto) {
        Session session = null;
        Transaction tx = null;
        Match match = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            match = new Match();
            match.setEpreuve(epreuvesRepository.getById(matchDto.getEpreuvesFullDto().getId()));
            match.setVainqueur(joueurRepository.getById(matchDto.getVainqueur().getId()));
            match.setFinaliste(joueurRepository.getById(matchDto.getFinaliste().getId()));

            Score score = new Score();
            score.setMatch(match);
            match.setScore(score);
            score.setSet1(matchDto.getScoreFullDto().getSet1());
            score.setSet2(matchDto.getScoreFullDto().getSet2());
            score.setSet3(matchDto.getScoreFullDto().getSet3());
            score.setSet4(matchDto.getScoreFullDto().getSet4());
            score.setSet5(matchDto.getScoreFullDto().getSet5());

            matchRepository.createMatch(match);


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

    }

    public void deleteMatch(Long id) {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            matchRepository.delete(id);

            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void tapisVert(Long id) {

        Session session = null;
        Transaction tx = null;
        Match match = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            match = matchRepository.getById(id);

            Joueur ancienVainqueur = match.getVainqueur();
            match.setVainqueur(match.getFinaliste());
            match.setFinaliste(ancienVainqueur);

            match.getScore().setSet1((byte)0);
            match.getScore().setSet2((byte)0);
            match.getScore().setSet3((byte)0);
            match.getScore().setSet4((byte)0);
            match.getScore().setSet5((byte)0);

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
    }

    public MatchDto getMatch(Long id) {

        Session session = null;
        Transaction tx = null;
        Match match = null;
        MatchDto matchDto = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            match = matchRepository.getById(id);

            matchDto = new MatchDto();
            matchDto.setId(match.getId());

            JoueursDto finalisteDto = new JoueursDto();
            finalisteDto.setId(match.getFinaliste().getId());
            finalisteDto.setPrenom(match.getFinaliste().getPrenom());
            finalisteDto.setNom(match.getFinaliste().getNom());
            finalisteDto.setSexe(match.getFinaliste().getSexe());
            matchDto.setFinaliste(finalisteDto);

            JoueursDto vainqueurDto = new JoueursDto();
            vainqueurDto.setId(match.getVainqueur().getId());
            vainqueurDto.setPrenom(match.getVainqueur().getPrenom());
            vainqueurDto.setNom(match.getVainqueur().getNom());
            vainqueurDto.setSexe(match.getVainqueur().getSexe());
            matchDto.setVainqueur(vainqueurDto);

            EpreuvesFullDto epreuvesFullDto = new EpreuvesFullDto();
            epreuvesFullDto.setId(match.getEpreuve().getId()) ;
            epreuvesFullDto.setAnnee(match.getEpreuve().getAnnee());
            epreuvesFullDto.setTypeEpreuve(match.getEpreuve().getTypeEpreuve());

            TournoiDto tournoiDto = new TournoiDto();
            tournoiDto.setId(match.getEpreuve().getId());
            tournoiDto.setCode(match.getEpreuve().getTournoi().getCode());
            tournoiDto.setNom(match.getEpreuve().getTournoi().getNom());
            tournoiDto.setCode(match.getEpreuve().getTournoi().getCode());
            epreuvesFullDto.setTournoiDto(tournoiDto);

            matchDto.setEpreuvesFullDto(epreuvesFullDto);

            ScoreFullDto scoreFullDto = new ScoreFullDto();
            scoreFullDto.setId(match.getScore().getId());
            scoreFullDto.setSet1(match.getScore().getSet1());
            scoreFullDto.setSet2(match.getScore().getSet2());
            scoreFullDto.setSet3(match.getScore().getSet3());
            scoreFullDto.setSet4(match.getScore().getSet4());
            scoreFullDto.setSet5(match.getScore().getSet5());

            matchDto.setScoreFullDto(scoreFullDto);
            scoreFullDto.setMatchDto(matchDto);

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

        return matchDto;
    }
}

