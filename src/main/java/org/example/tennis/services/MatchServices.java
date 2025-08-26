package org.example.tennis.services;

import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.EpreuvesFullDto;
import org.example.tennis.dto.JoueursDto;
import org.example.tennis.dto.MatchDto;
import org.example.tennis.dto.TournoiDto;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Match;
import org.example.tennis.repository.MatchRepositoryImpl;
import org.example.tennis.repository.ScoreRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

