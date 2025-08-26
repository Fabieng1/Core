package org.example.tennis.dto;

import org.example.tennis.entity.Tournoi;

public class EpreuvesFullDto {

    private Long id;
    private Short annee;

    private TournoiDto tournoiDto;

    private Character typeEpreuve;

    public Character getTypeEpreuve() {
        return typeEpreuve;
    }

    public void setTypeEpreuve(Character typeEpreuve) {
        this.typeEpreuve = typeEpreuve;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getAnnee() {
        return annee;
    }

    public void setAnnee(Short annee) {
        this.annee = annee;
    }

    public TournoiDto getTournoiDto() {
        return tournoiDto;
    }

    public void setTournoiDto(TournoiDto tournoiDto) {
        this.tournoiDto = tournoiDto;
    }
}
