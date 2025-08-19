package org.example.tennis.repository;

import org.example.tennis.DataSourceProvider;
import org.example.tennis.HibernateUtil;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Tournoi;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournoiRepositoryImpl {

    public Tournoi createTournoi (Tournoi tournoi) {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(tournoi);
            tx.commit();

            System.out.println("Tournoi créé !");

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
        return tournoi;
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

    public void deletePlayer (Long id) {

        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);


            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM TOURNOI WHERE ID = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Tournoi supprimé ! !");

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

    public Tournoi getById (Long id) {

        Connection conn = null;
        Tournoi tournoi = null;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tournoi = session.get(Tournoi.class, id);
            System.out.println("Tournoi lu !");
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

        finally {
            if (session != null) {
                session.close();
            }
        }


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
