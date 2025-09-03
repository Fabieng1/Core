package org.example.tennis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.tennis.EntityManagerHolder;
import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.JoueursDto;
import org.example.tennis.entity.Joueur;
import org.example.tennis.repository.JoueurRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoueursServices {

    private JoueurRepositoryImpl joueurRepository;

    public JoueursServices() {

        this.joueurRepository = new JoueurRepositoryImpl();
    }

    public void createPlayer(Joueur joueur) {

        joueurRepository.createPlayer(joueur);
    }

    public List<JoueursDto> getListeJoueurs(char sexe) {

        Map em = null;
        EntityTransaction tx = null;

        List<JoueursDto> joueursDtoList = new ArrayList<>();


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("tennis-unit");

        em = EntityManagerHolder.getAll();
        List<Joueur> joueurList = joueurRepository.listPlayer(sexe);


        for (Joueur joueur : joueurList) {

            final JoueursDto joueursDto = new JoueursDto();
            joueursDto.setId(joueur.getId());
            joueursDto.setPrenom(joueur.getPrenom());
            joueursDto.setNom(joueur.getNom());
            joueursDto.setSexe(joueur.getSexe());
            joueursDtoList.add(joueursDto);


        }
        tx.commit();


        return joueursDtoList;
    }

    public Joueur getPlayer(Long id) {

        EntityManager em = null;
        EntityTransaction tx = null;
        Joueur joueur = null;

        try {

            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();

            joueur = joueurRepository.getById(id);
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

        return joueur;
    }

    public void renomme(Long id, String newName) {

        Joueur joueur = getPlayer(id);

        EntityManager em = null;
        EntityTransaction tx = null;

        try {

            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();

            joueur.setNom(newName);
            Joueur joueur2 = (Joueur) em.merge(joueur);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void sexChange(Long id, char newSex) {

        Joueur joueur = getPlayer(id);

        EntityManager em = null;
        EntityTransaction tx = null;

        try {

            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();

            joueur.setSexe(newSex);
            Joueur joueur2 = (Joueur) em.merge(joueur);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deletePlayer(Long id) {

        EntityManager em = null;
        EntityTransaction tx = null;

        try {

            em = EntityManagerHolder.getCurrentEntityManager();
            tx = em.getTransaction();
            tx.begin();

            joueurRepository.delete(id);
            tx.commit();
        }
        finally{
            if (em != null) {
                em.close();
            }
        }
    }

}
