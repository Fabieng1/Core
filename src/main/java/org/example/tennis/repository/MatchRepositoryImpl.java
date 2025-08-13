package org.example.tennis.repository;

import org.example.tennis.DataSourceProvider;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Match;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchRepositoryImpl {

    public void createMatch (Match match) {

        Connection conn = null;
        try {

            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO MATCH (ID EPREUVE, ID VAINQUEUR, ID FINALISTE) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, match.getEpreuve().getId());
            preparedStatement.setLong(2, match.getVainqueur().getId());
            preparedStatement.setLong(3, match.getFinaliste().getId());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if(rs.next()) {
                match.setId(rs.getLong(1));
            }

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Match créé !");

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
