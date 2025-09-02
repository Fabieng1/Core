package org.example.tennis.services;

import jakarta.persistence.*;
import jakarta.persistence.EntityManager;
import org.example.tennis.EntityManagerHolder;
import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.TournoiDto;
import org.example.tennis.entity.Tournoi;
import org.example.tennis.repository.TournoiRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class TournoisServices {

    private TournoiRepositoryImpl tournoiRepository;

    public TournoisServices() {
        this.tournoiRepository = new TournoiRepositoryImpl();
    }

    public TournoiDto getTournoi(Long id) {

        //Session session = null;
        EntityManager em = null;
        EntityTransaction tx = null;
        Tournoi tournoi = null;
        TournoiDto tournoiDto = null;

        try {

            //session = HibernateUtil.getSessionFactory().getCurrentSession();
            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();
            //tx = session.beginTransaction();
            tournoi = tournoiRepository.getById(id);
            tournoiDto = new TournoiDto();
            tournoiDto.setId(tournoi.getId());
            tournoiDto.setCode(tournoi.getCode());
            tournoiDto.setNom(tournoi.getNom());
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }

        finally {
            if (em != null) {
                em.close();
            }
        }

        return tournoiDto;
    }

    public Tournoi getTournois(Long id) {
        TournoiRepositoryImpl tournoiRepository = new TournoiRepositoryImpl();

        return tournoiRepository.getById(id);
    }

    public void createTournoi(TournoiDto tournoiDto) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {

            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();
            //session = HibernateUtil.getSessionFactory().openSession();
            Tournoi tournoi = new Tournoi();
            tournoi.setId(tournoiDto.getId());
            tournoi.setCode(tournoiDto.getCode());
            tournoi.setNom(tournoiDto.getNom());
            tournoiRepository.createTournoi(tournoi);

            tx.commit();

            System.out.println("Tournoi créé !");

        } catch (Throwable t) {
            if (tx != null) {
                tx.rollback();
            }
            t.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteTournoi(Long id) {

        EntityManager em = null;
        EntityTransaction tx = null;

        try {

            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();

            tournoiRepository.delete(id);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
}

