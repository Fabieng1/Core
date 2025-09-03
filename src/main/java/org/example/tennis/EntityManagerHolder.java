package org.example.tennis;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.tennis.entity.Joueur;
import org.example.tennis.repository.JoueurRepositoryImpl;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManagerHolder {
    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("tennis-unit");
    }


    private EntityManagerHolder() {

    }

    /**
     * @return The {@link EntityManager} linked to this thread
     */
    public static EntityManager getCurrentEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();

        if (entityManager == null) {

            // Start the conversation by creating the EntityManager for this thread
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);

        }
        return entityManager;
    }

    public static Map<String, List<Joueur>> getAll() {
        EntityManager em = getCurrentEntityManager();
        Map<String, List<Joueur>> result = new HashMap<>();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            JoueurRepositoryImpl repo = new JoueurRepositoryImpl();
            List<Joueur> hommes = repo.listPlayer('H');
            List<Joueur> femmes = repo.listPlayer('F');

            tx.commit();

            result.put("hommes", hommes);
            result.put("femmes", femmes);

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e; // pour voir l'erreur dans les logs
        } finally {
            if (em.isOpen()) {
                em.close();
                entityManagerThreadLocal.remove(); // éviter de réutiliser un EM fermé
            }
        }

        return result;
    }


}