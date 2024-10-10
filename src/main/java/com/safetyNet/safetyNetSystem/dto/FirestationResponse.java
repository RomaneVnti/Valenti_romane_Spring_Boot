package com.safetyNet.safetyNetSystem.dto;

import java.util.List;

public class FirestationResponse {
    private List<PersonInfo> persons;
    private int numberOfAdults;
    private int numberOfChildren;

    public FirestationResponse(List<PersonInfo> persons, int numberOfAdults, int numberOfChildren) {
        this.persons = persons;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }

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
}
