package org.example.tennis.repository;

import org.example.tennis.DataSourceProvider;
import org.example.tennis.HibernateUtil;
import org.example.tennis.dto.MatchDto;
import org.example.tennis.entity.Epreuve;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Match;
import org.example.tennis.entity.Tournoi;
import org.hibernate.Session;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchRepositoryImpl {

    public void createMatch (Match match) {

      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.persist(match);
        System.out.println("Match ajouté !");
    }

    public Match getById (Long id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Match match = session.get(Match.class, id);
        System.out.println("Match lu !");

        return match;
    }

    public void delete(Long id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Match match = session.get(Match.class, id);
        session.delete(match);

        System.out.println("Match supprimé !");
    }
}
