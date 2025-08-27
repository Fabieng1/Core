package org.example.tennis.dto;

import org.example.tennis.entity.Tournoi;

import java.util.Set;

public class EpreuvesFullDto {

    private Long id;
    private Short annee;

    private TournoiDto tournoiDto;

    private Character typeEpreuve;

    private Set<JoueursDto> participantsDto;
    public Character getTypeEpreuve() {
        return typeEpreuve;
    }

    public Set<JoueursDto> getParticipantsDto() {
        return participantsDto;
    }

    public void setParticipantsDto(Set<JoueursDto> participantsDto) {
        this.participantsDto = participantsDto;
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
