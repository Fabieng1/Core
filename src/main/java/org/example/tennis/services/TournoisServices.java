package org.example.tennis.services;

import jakarta.persistence.EntityManager;
import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.TournoiDto;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Tournoi;
import org.example.tennis.repository.TournoiRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mycompany.tennis.EntityManagerHolder;

public class TournoisServices {

    private TournoiRepositoryImpl tournoiRepository;

    public TournoisServices() {
        this.tournoiRepository = new TournoiRepositoryImpl();
    }

    public TournoiDto getTournoi(Long id) {

        //Session session = null;
        EntityManager em = null;
        Transaction tx = null;
        Tournoi tournoi = null;
        TournoiDto tournoiDto = null;

        try {

            //session = HibernateUtil.getSessionFactory().getCurrentSession();
            em = new EntityManagerHolder().getCurrentEntityManager();

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

        return tournoiDto;
    }

    public Tournoi getTournois(Long id) {
        TournoiRepositoryImpl tournoiRepository = new TournoiRepositoryImpl();

        return tournoiRepository.getById(id);
    }

    public void createTournoi(TournoiDto tournoiDto) {
        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Tournoi tournoi = new Tournoi();
            tournoi.setId(tournoiDto.getId());
            tournoi.setCode(tournoiDto.getCode());
            tournoi.setNom(tournoiDto.getNom());
            session.persist(tournoi);
            tx.commit();

            System.out.println("Tournoi créé !");

        } catch (Throwable t) {
            if (tx != null) {
                tx.rollback();
            }
            t.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void deleteTournoi(Long id) {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

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
            if (session != null) {
                session.close();
            }
        }
    }
}

