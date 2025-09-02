package org.example.tennis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.tennis.EntityManagerHolder;
import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.EpreuvesFullDto;
import org.example.tennis.dto.EpreuvesLightDto;
import org.example.tennis.dto.JoueursDto;
import org.example.tennis.dto.TournoiDto;
import org.example.tennis.entity.Epreuve;
import org.example.tennis.entity.Joueur;
import org.example.tennis.repository.EpreuvesRepositoryImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EpreuvesServices {

    private EpreuvesRepositoryImpl epreuvesRepository;

    public EpreuvesServices() {

        this.epreuvesRepository = new EpreuvesRepositoryImpl();
    }
    public EpreuvesFullDto getEpreuveDetaillee(Long id) {

        Session session = null;
        Transaction tx = null;
        Epreuve epreuve = null;
        EpreuvesFullDto epreuvesFullDto = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            epreuve = epreuvesRepository.getById(id);

            epreuvesFullDto = new EpreuvesFullDto();
            epreuvesFullDto.setId(epreuve.getId());
            epreuvesFullDto.setAnnee(epreuvesFullDto.getAnnee());
            epreuvesFullDto.setTypeEpreuve(epreuve.getTypeEpreuve());

            TournoiDto tournoiDto = new TournoiDto();
            tournoiDto.setId(epreuve.getId());
            tournoiDto.setNom(epreuve.getTournoi().getNom());
            tournoiDto.setCode(epreuve.getTournoi().getCode());
            epreuvesFullDto.setTournoiDto(tournoiDto);

            epreuvesFullDto.setParticipantsDto (new HashSet<>());

            for (Joueur joueur : epreuve.getParticipants()) {
                final JoueursDto joueursDto = new JoueursDto();

                joueursDto.setId(joueur.getId());
                joueursDto.setPrenom(joueur.getPrenom());
                joueursDto.setNom(joueur.getNom());
                joueursDto.setSexe(joueur.getSexe());
                epreuvesFullDto.getParticipantsDto().add(joueursDto);
            }

            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        finally {
            if (session != null) {
                session.close();
            }
        }

        return epreuvesFullDto;
    }

    public EpreuvesLightDto getEpreuveSansTournoi(Long id) {

        Session session = null;
        Transaction tx = null;
        Epreuve epreuve = null;
        EpreuvesLightDto epreuvesLightDtoDtoDto = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            epreuve = epreuvesRepository.getById(id);
            EpreuvesLightDto epreuvesLightDto = new EpreuvesLightDto();
            epreuvesLightDto.setId(epreuve.getId());
            epreuvesLightDto.setAnnee(epreuvesLightDto.getAnnee());
            epreuvesLightDto.setTypeEpreuve(epreuve.getTypeEpreuve());
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        finally {
            if (session != null) {
                session.close();
            }
        }

        return epreuvesLightDtoDtoDto;
    }

    public List<EpreuvesFullDto> getListeJoueurs(String codeTournoi) {

        EntityManager em = null;
        EntityTransaction tx = null;


        List<EpreuvesFullDto> epreuvesFullDto = new ArrayList<>();


        try {

            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();

            List<Epreuve> epreuves =  epreuvesRepository.listPlayer(codeTournoi);


            for (Epreuve epreuve : epreuves) {

                final EpreuvesFullDto epreuvesDto = new EpreuvesFullDto();
                epreuvesDto.setId(epreuve.getId());
                epreuvesDto.setAnnee(epreuve.getAnnee());
                epreuvesDto.setTypeEpreuve(epreuve.getTypeEpreuve());

                TournoiDto tournoiDto = new TournoiDto();
                tournoiDto.setId(epreuve.getTournoi().getId());
                tournoiDto.setCode(epreuve.getTournoi().getCode());
                tournoiDto.setNom(epreuve.getTournoi().getNom());

                epreuvesDto.setTournoiDto(tournoiDto);
                epreuvesFullDto.add(epreuvesDto);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return epreuvesFullDto;
    }
}
