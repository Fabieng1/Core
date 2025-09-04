package org.example.tennis;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.tennis.entity.Joueur;
import org.example.tennis.repository.JoueurRepositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestionnaire centralisé d'EntityManager pour l'application.
 */
public class EntityManagerHolder {

    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private EntityManagerHolder() { }

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("tennis-unit");
    }

    /**
     * @return l'EntityManager lié au thread courant
     */
    public static EntityManager getCurrentEntityManager() {
        EntityManager em = entityManagerThreadLocal.get();

        if (em == null || !em.isOpen()) {
            em = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(em);
        }

        return em;
    }

    /**
     * Ferme l'EntityManager du thread courant si ouvert.
     */
    public static void closeCurrentEntityManager() {
        EntityManager em = entityManagerThreadLocal.get();
        if (em != null && em.isOpen()) {
            em.close();
        }
        entityManagerThreadLocal.remove();
    }

    /**
     * Récupère tous les joueurs hommes et femmes.
     * @return Map avec clés "hommes" et "femmes"
     */
    public static Map<String, List<Joueur>> getAll() {
        Map<String, List<Joueur>> result = new HashMap<>();
        EntityManager em = getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            JoueurRepositoryImpl repo = new JoueurRepositoryImpl();
            result.put("hommes", repo.listPlayer('H'));
            result.put("femmes", repo.listPlayer('F'));

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeCurrentEntityManager(); // Toujours fermer l'EM après usage
        }

        return result;
    }
}
