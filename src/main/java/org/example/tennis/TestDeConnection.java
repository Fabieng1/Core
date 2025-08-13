package org.example.tennis;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.tennis.entity.Joueur;
import org.example.tennis.entity.Match;
import org.example.tennis.entity.Score;
import org.example.tennis.entity.Tournoi;
import org.example.tennis.repository.JoueurRepositoryImpl;
import org.example.tennis.repository.TournoiRepositoryImpl;
import org.example.tennis.services.JoueursServices;
import org.example.tennis.services.MatchServices;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDeConnection {

    public static void main (String[] args) {
        MatchServices matchServices = new MatchServices();
        Match match = new Match();
        Score score = new Score();
        score.setSet1((byte)3);
        score.setSet2((byte)4);
        score.setSet3((byte)6);
        System.out.println("Match : " + match.getId());

    }
}