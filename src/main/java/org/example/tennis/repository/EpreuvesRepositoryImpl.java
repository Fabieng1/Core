package org.example.tennis.repository;

import org.example.tennis.HibernateUtil;
import org.example.tennis.entity.Epreuve;
import org.example.tennis.entity.Tournoi;
import org.hibernate.Session;

public class EpreuvesRepositoryImpl {

    public Epreuve getById (Long id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Epreuve epreuve = session.get(Epreuve.class, id);
        System.out.println("Epreuve lu !");

        return epreuve;
    }
}
