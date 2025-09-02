package org.example.tennis.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.tennis.EntityManagerHolder;
import org.example.tennis.entity.Epreuve;

import java.util.List;

public class EpreuvesRepositoryImpl {

    public Epreuve getById (Long id) {

        EntityManager em = EntityManagerHolder.getCurrentEntityManager();
        Epreuve epreuve = em.find(Epreuve.class, id);
        System.out.println("Epreuve lu !");

        return epreuve;
    }

    public List<Epreuve> listPlayer(String codeTournoi) {

        EntityManager em = EntityManagerHolder.getCurrentEntityManager();

        TypedQuery<Epreuve> query = em.createQuery("select e from Epreuve e join fetch e.tournoi where e.tournoi.code = ?1", Epreuve.class);
        query.setParameter(1, codeTournoi);

        List<Epreuve> epreuves = query.getResultList();

        System.out.println("Epreuve lu !");

        return epreuves;
    }
}
