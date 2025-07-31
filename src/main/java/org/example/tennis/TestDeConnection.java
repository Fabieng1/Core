package org.example.tennis;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class TestDeConnection {

    public static void main (String[] args) {

        Connection conn = null;
        try {

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setInitialSize(5);
            dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
            dataSource.setUsername("root");
            dataSource.setPassword("F@bien");
            conn = dataSource.getConnection();


            //Seulement avant Java 7/JDBC 4
            //Class.forName(DRIVER_CLASS_NAME);

            //MySQL driver MySQL Connector
            // conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris","root","F@bien");

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (PRENOM, NOM, SEXE) VALUES (?, ?, ?)");
            String prenom = "Jenifer";
            String nom = "Capriati";
            String sexe = "F";
            preparedStatement.setString(1, prenom);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, sexe);

            preparedStatement.executeUpdate();

            prenom = "Thomas";
            nom = "Johanson";
            sexe = "H";
            preparedStatement.setString(1, prenom);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, sexe);

            preparedStatement.executeUpdate();

            conn.commit();

            Statement statement = conn.createStatement();

            //Oracle Driver officiel OJDBC Thin
            //conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:tennis","COURSDB","COURSDB");
            //Postgres Driver officiel
            //conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tennis","COURSDB","COURSDB");
            System.out.println("success");

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
}
