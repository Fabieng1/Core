package org.example.tennis.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.tennis.DataSourceProvider;
import org.example.tennis.HibernateUtil;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Tournoi;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurRepositoryImpl {

    public void createPlayer(Joueur joueur) {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            session.persist(joueur);
            tx.commit();

            System.out.println("Joueur créé !");

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

    public void delete(Long id) {

        Joueur joueur = getById(id);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.delete(joueur);
    }

    public Joueur getById(Long id) {

        Joueur joueur = null;
        Session session = null;

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        joueur = session.get(Joueur.class, id);
        System.out.println("Joueur lu !");

        return joueur;
    }

    public List<Joueur> listPlayer(char sexe) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Query<Joueur> query = session.createNamedQuery("given_sexe", Joueur.class);
        query.setParameter(1, sexe);

        List<Joueur> joueurs = query.getResultList();

        System.out.println("Joueurs lu !");

        return joueurs;
    }


}