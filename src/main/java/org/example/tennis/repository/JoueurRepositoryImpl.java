package org.example.tennis.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.tennis.entity.Joueur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurRepositoryImpl {

    public void createPlayer (Joueur joueur) {

        Connection conn = null;
        try {

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setInitialSize(5);
            dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
            dataSource.setUsername("root");
            dataSource.setPassword("F@bien");
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (PRENOM, NOM, SEXE) VALUES (?, ?, ?)");
            preparedStatement.setString(1, joueur.getPrenom());
            preparedStatement.setString(2, joueur.getNom());
            preparedStatement.setString(3, joueur.getSexe().toString());

            preparedStatement.executeUpdate();

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Joueur créé !");

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

    public void UpdatePlayer (Joueur joueur) {

        Connection conn = null;
        try {

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setInitialSize(5);
            dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
            dataSource.setUsername("root");
            dataSource.setPassword("F@bien");
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE INTO JOUEUR (PRENOM = ?, NOM = ?, SEXE = ? WHERE ID = ?)");
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

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setInitialSize(5);
            dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
            dataSource.setUsername("root");
            dataSource.setPassword("F@bien");
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("DELETE JOUEUR WHERE ID = ?");
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

    public Joueur getById (Long id) {

        Connection conn = null;
        Joueur joueur = null;

        try {

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setInitialSize(5);
            dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
            dataSource.setUsername("root");
            dataSource.setPassword("F@bien");
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT PRENOM, NOM, SEXE JOUEUR WHERE ID = ?");
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                joueur = new Joueur();

                joueur.setId(id);
                joueur.setPrenom(rs.getString("PRENOM"));
                joueur.setNom(rs.getString("NOM"));
                joueur.setSexe(rs.getString("SEXE").charAt(0));
            }

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Joueur lu ! !");

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

        return joueur;
    }

    public List<Joueur> list(Long id) {

        Connection conn = null;
        List<Joueur> listJoueurs = new ArrayList<>();

        try {

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setInitialSize(5);
            dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
            dataSource.setUsername("root");
            dataSource.setPassword("F@bien");
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT ID, PRENOM, NOM, SEXE JOUEUR WHERE ID = ?");

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
        }

        return listJoueurs;
    }
}
