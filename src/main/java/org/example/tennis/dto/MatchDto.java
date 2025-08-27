package org.example.tennis.dto;

public class MatchDto {

    public MatchDto() {

    }

    Long id = null;

    private JoueursDto vainqueur;
    private JoueursDto finaliste;
    private EpreuvesFullDto epreuvesFullDto;
    private ScoreFullDto scoreFullDto;

    public ScoreFullDto getScoreFullDto() {
        return scoreFullDto;
    }

    public void setScoreFullDto(ScoreFullDto scoreFullDto) {
        this.scoreFullDto = scoreFullDto;
    }

    public EpreuvesFullDto getEpreuvesFullDto() {
        return epreuvesFullDto;
    }

    public void setEpreuvesFullDto(EpreuvesFullDto epreuvesFullDto) {
        this.epreuvesFullDto = epreuvesFullDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JoueursDto getVainqueur() {
        return vainqueur;
    }

    public void setVainqueur(JoueursDto vainqueur) {
        this.vainqueur = vainqueur;
    }

    public JoueursDto getFinaliste() {
        return finaliste;
    }

    public void setFinaliste(JoueursDto finaliste) {
        this.finaliste = finaliste;
    }
}
