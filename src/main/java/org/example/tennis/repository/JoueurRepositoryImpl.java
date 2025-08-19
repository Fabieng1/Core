package org.example.tennis.repository;

import org.apache.commons.dbcp2.BasicDataSource;
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

public class JoueurRepositoryImpl {

    public void createPlayer(Joueur joueur) {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(joueur);
            tx.commit();

            System.out.println("Joueur créé !");

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
    }

    public void updatePlayer(Joueur joueur) {

        Connection conn = null;

        try {

            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE JOUEUR SET PRENOM = ?, NOM = ?, SEXE = ? WHERE ID = ?");
            preparedStatement.setString(1, joueur.getPrenom());
            preparedStatement.setString(2, joueur.getNom());
            preparedStatement.setString(3, joueur.getSexe().toString());
            preparedStatement.setLong(4, joueur.getId());

            preparedStatement.executeUpdate();

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Joueur modifié !");

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

    }

    public void deletePlayer(Long id) {

        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);


            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Joueur supprimé ! !");

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

    }

    public Joueur getById(Long id) {

        Connection conn = null;
        Joueur joueur = null;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();
            joueur = session.get(Joueur.class, id);
            System.out.println("Joueur lu !");
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            return joueur;
        }
    }

    public List<Joueur> listPlayer() {

        Connection conn = null;
        List<Joueur> listJoueurs = new ArrayList<>();

        try {

            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT ID, PRENOM, NOM, SEXE FROM JOUEUR");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Joueur joueur = new Joueur();

                joueur.setId(rs.getLong("ID"));
                joueur.setPrenom(rs.getString("PRENOM"));
                joueur.setNom(rs.getString("NOM"));
                joueur.setSexe(rs.getString("SEXE").charAt(0));
                listJoueurs.add(joueur);
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


            return listJoueurs;
        }
    }
}