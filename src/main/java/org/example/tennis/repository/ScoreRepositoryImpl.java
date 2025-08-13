package org.example.tennis.repository;

import org.example.tennis.DataSourceProvider;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Score;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreRepositoryImpl {

    public void createScore (Score score) {

        Connection conn = null;
        try {

            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();
            conn = dataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO SCORE VAINQUEUR (ID MATCH, SET_1, SET_2, SET_3, SET_4, SET_5) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setByte(1, score.getSet1());
            preparedStatement.setByte(2, score.getSet2());
            preparedStatement.setByte(3, score.getSet3());
            preparedStatement.setByte(4, score.getSet4());
            preparedStatement.setByte(5, score.getSet5());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if(rs.next()) {
                score.setId(rs.getLong(1));
            }

            conn.commit();

            Statement statement = conn.createStatement();

            System.out.println("Score créé !");

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
