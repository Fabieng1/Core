package org.example.tennis;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.tennis.entity.*;
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
        match.setScore(score);
        score.setMatch(match);

        Joueur federer = new Joueur();
        federer.setId(32L);

        Joueur murray = new Joueur();
        murray.setId(34L);

        match.setVainqueur(federer);
        match.setFinaliste(murray);

        Epreuve epreuve = new Epreuve();
        epreuve.setId(9L);
        match.setEpreuve(epreuve);

        matchServices.enregistrezVosMatch(match);

        System.out.println(match.getId());
    }
}