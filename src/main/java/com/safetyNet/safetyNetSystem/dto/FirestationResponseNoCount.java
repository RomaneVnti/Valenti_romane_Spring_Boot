package com.safetyNet.safetyNetSystem.dto;

import java.util.List;

public class FirestationResponseNoCount {
    private List<PersonInfo> persons;
    private String stationNumber;

    // Constructeur sans les éléments `numberOfAdults` et `numberOfChildren`
    public FirestationResponseNoCount(List<PersonInfo> persons, String stationNumber) {
        this.persons = persons;
        this.stationNumber = stationNumber;
    }

    // Getters et setters
    public List<PersonInfo> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonInfo> persons) {
        this.persons = persons;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }
}
