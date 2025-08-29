package org.example.tennis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.tennis.DataSourceProvider;
import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.JoueursDto;
import org.example.tennis.entity.Joueur;
import org.example.tennis.repository.JoueurRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JoueursServices {

    private JoueurRepositoryImpl joueurRepository;

    public JoueursServices() {

        this.joueurRepository = new JoueurRepositoryImpl();
    }

    public void createPlayer(Joueur joueur) {

        joueurRepository.createPlayer(joueur);
    }

    public List<JoueursDto> getListeJoueurs(char sexe) {

        Session session = null;
        Transaction tx = null;
        List<JoueursDto> joueursDtoList = new ArrayList<>();


        try {

            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("tennis-unit");

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            List<Joueur> joueurList =  joueurRepository.listPlayer(sexe);


            for (Joueur joueur : joueurList) {

                final JoueursDto joueursDto = new JoueursDto();
                joueursDto.setId(joueur.getId());
                joueursDto.setPrenom(joueur.getPrenom());
                joueursDto.setNom(joueur.getNom());
                joueursDto.setSexe(joueur.getSexe());
                joueursDtoList.add(joueursDto);
            }

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

        return joueursDtoList;
    }

    public Joueur getPlayer(Long id) {

        Session session = null;
        Transaction tx = null;
        Joueur joueur = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            joueur = joueurRepository.getById(id);
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

        return joueur;
    }

    public void renomme(Long id, String newName) {

        Joueur joueur = getPlayer(id);

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            joueur.setNom(newName);
            Joueur joueur2 = (Joueur) session.merge(joueur);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void sexChange(Long id, char newSex) {

        Joueur joueur = getPlayer(id);

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            joueur.setSexe(newSex);
            Joueur joueur2 = (Joueur) session.merge(joueur);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void deletePlayer(Long id) {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            joueurRepository.delete(id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}