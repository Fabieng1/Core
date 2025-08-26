package org.example.tennis.repository;

import org.example.tennis.DataSourceProvider;
import org.example.tennis.HibernateUtil;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Score;
import org.hibernate.Session;

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

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO SCORE_VAINQUEUR (ID_MaTCH, SET_1, SET_2, SET_3, SET_4, SET_5) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, score.getMatch().getId());
            preparedStatement.setByte(2, score.getSet1());
            preparedStatement.setByte(3, score.getSet2());
            if (score.getSet3() == null) {
                preparedStatement.setNull(4, Types.TINYINT);
            }
            else {
                preparedStatement.setByte(4, score.getSet3());
            }
            if (score.getSet4() == null) {
                preparedStatement.setNull(5, Types.TINYINT);
            }
            else {
                preparedStatement.setByte(5, score.getSet4());
            }
            if (score.getSet5() == null) {
                preparedStatement.setNull(6, Types.TINYINT);
            }
            else {
                preparedStatement.setByte(6, score.getSet5());
            }

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

    public Score getById(Long id) {

        Score score = null;
        Session session = null;

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        score = session.get(Score.class, id);
        System.out.println("Score lu !");

        return score;
    }
}