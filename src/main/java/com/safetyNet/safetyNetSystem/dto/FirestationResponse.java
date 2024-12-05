package com.safetyNet.safetyNetSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirestationResponse {
    private List<PersonInfo> persons;
    private int numberOfAdults;
    private int numberOfChildren;
    private String stationNumber; // Numéro de la caserne

    // Constructeur par défaut (requis pour Jackson)
    public FirestationResponse() {
    }

    // Constructeur pour les personnes et le nombre d'adultes/enfants
    public FirestationResponse(List<PersonInfo> persons, int numberOfAdults, int numberOfChildren) {
        this.persons = persons;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }

    // Constructeur pour les personnes et le numéro de la caserne
    public FirestationResponse(List<PersonInfo> persons, String stationNumber) {
        this.persons = persons;
        this.stationNumber = stationNumber;
        this.numberOfAdults = 0; // Optionnel
        this.numberOfChildren = 0; // Optionnel
    }

    // Getters et setters
    public List<PersonInfo> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonInfo> persons) {
        this.persons = persons;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }
}
