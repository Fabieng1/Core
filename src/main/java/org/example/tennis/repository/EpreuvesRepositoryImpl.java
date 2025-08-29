package org.example.tennis.repository;

import org.example.tennis.HibernateUtil;
import org.example.tennis.entity.Epreuve;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Tournoi;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EpreuvesRepositoryImpl {

    public Epreuve getById (Long id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Epreuve epreuve = session.get(Epreuve.class, id);
        System.out.println("Epreuve lu !");

        return epreuve;
    }

    public List<Epreuve> listPlayer(String codeTournoi) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Query<Epreuve> query = session.createQuery("select e from Epreuve e join fetch e.tournoi where e.tournoi.code = ?1", Epreuve.class);
        query.setParameter(1, codeTournoi);

        List<Epreuve> epreuves = query.getResultList();

        System.out.println("Epreuve lu !");

        return epreuves;
    }
}
