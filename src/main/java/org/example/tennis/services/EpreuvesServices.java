package org.example.tennis.services;

import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.EpreuvesFullDto;
import org.example.tennis.dto.EpreuvesLightDto;
import org.example.tennis.dto.TournoiDto;
import org.example.tennis.entity.Epreuve;
import org.example.tennis.repository.EpreuvesRepositoryImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EpreuvesServices {

    private EpreuvesRepositoryImpl epreuvesRepository;

    public EpreuvesServices() {

        this.epreuvesRepository = new EpreuvesRepositoryImpl();
    }
    public EpreuvesFullDto getEpreuveAvecTournoi(Long id) {

        Session session = null;
        Transaction tx = null;
        Epreuve epreuve = null;
        EpreuvesFullDto epreuvesFullDto = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            epreuve = epreuvesRepository.getById(id);

            epreuvesFullDto = new EpreuvesFullDto();
            epreuvesFullDto.setId(epreuve.getId());
            epreuvesFullDto.setAnnee(epreuvesFullDto.getAnnee());
            epreuvesFullDto.setTypeEpreuve(epreuve.getTypeEpreuve());

            TournoiDto tournoiDto = new TournoiDto();
            tournoiDto.setId(epreuve.getId());
            tournoiDto.setNom(epreuve.getTournoi().getNom());
            tournoiDto.setCode(epreuve.getTournoi().getCode());
            epreuvesFullDto.setTournoiDto(tournoiDto);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        finally {
            if (session != null) {
                session.close();
            }
        }

        return epreuvesFullDto;
    }

    public EpreuvesLightDto getEpreuveSansTournoi(Long id) {

        Session session = null;
        Transaction tx = null;
        Epreuve epreuve = null;
        EpreuvesLightDto epreuvesLightDtoDtoDto = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            epreuve = epreuvesRepository.getById(id);
            EpreuvesLightDto epreuvesLightDto = new EpreuvesLightDto();
            epreuvesLightDto.setId(epreuve.getId());
            epreuvesLightDto.setAnnee(epreuvesLightDto.getAnnee());
            epreuvesLightDto.setTypeEpreuve(epreuve.getTypeEpreuve());
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        finally {
            if (session != null) {
                session.close();
            }
        }

        return epreuvesLightDtoDtoDto;
    }
}
