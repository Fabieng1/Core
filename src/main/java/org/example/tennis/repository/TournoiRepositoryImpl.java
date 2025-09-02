package org.example.tennis.repository;

import org.example.tennis.DataSourceProvider;
import org.example.tennis.EntityManagerHolder;
import org.example.tennis.HibernateUtil;
import org.example.tennis.entity.Tournoi;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournoiRepositoryImpl {

    private TournoiRepositoryImpl tournoiRepository;

    public void createTournoi (Tournoi tournoi) {


        EntityManager em = EntityManagerHolder.getCurrentEntityManager();
        //session = HibernateUtil.getSessionFactory().openSession();
        em.persist(tournoi);

        System.out.println("Tournoi créé !");
    }
    public void updatePlayer (Tournoi tournoi) {

        Connection conn = null;

        try {

            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE TOURNOI SET NOM = ?, CODE = ? WHERE ID = ?");
            preparedStatement.setString(1, tournoi.getNom());
            preparedStatement.setString(2, tournoi.getCode());
            preparedStatement.setLong(3, tournoi.getId());

            preparedStatement.executeUpdate();

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Tournoi modifié !");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        finally {
            try {
                if (conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void delete(Long id) {
        Tournoi tournoi = new Tournoi();
        tournoi.setId(id);

        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        EntityManager em = EntityManagerHolder.getCurrentEntityManager();
        em.remove(tournoi);
    }

    public Tournoi getById (Long id) {

        EntityManager em = EntityManagerHolder.getCurrentEntityManager();
        //Session session = HibernateUtil.getSessionFactory().openSession();
        Tournoi tournoi = em.find(Tournoi.class, id);
        System.out.println("Tournoi lu !");

        return tournoi;
    }

    public List<Tournoi> listTournoi() {

        Connection conn = null;
        List<Tournoi> listTournoi = new ArrayList<>();

        try {

            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT ID, NOM, CODE FROM TOURNOI");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Tournoi tournoi = new Tournoi();

                tournoi.setId(rs.getLong("ID"));
                tournoi.setNom(rs.getString("NOM"));
                tournoi.setCode(rs.getString("CODE"));
                listTournoi.add(tournoi);
            }

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Joueurs lus ! !");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listTournoi;
    }


}
