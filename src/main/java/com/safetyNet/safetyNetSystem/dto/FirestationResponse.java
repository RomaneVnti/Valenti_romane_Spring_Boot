package com.safetyNet.safetyNetSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Réponse de l'API contenant les informations relatives aux personnes associées à une caserne de pompiers.
 * Cette classe inclut la liste des personnes, le nombre d'adultes et d'enfants, ainsi que le numéro de la caserne.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirestationResponse {
    private List<PersonInfo> persons; // Liste des personnes associées à la caserne
    private int numberOfAdults;       // Nombre d'adultes dans la caserne
    private int numberOfChildren;     // Nombre d'enfants dans la caserne
    private String stationNumber;     // Numéro de la caserne

    /**
     * Constructeur par défaut, requis pour la sérialisation avec Jackson.
     */
    public FirestationResponse() {
    }

    /**
     * Constructeur pour initialiser les personnes ainsi que le nombre d'adultes et d'enfants.
     *
     * @param persons Liste des personnes associées à la caserne.
     * @param numberOfAdults Nombre d'adultes dans la caserne.
     * @param numberOfChildren Nombre d'enfants dans la caserne.
     */
    public FirestationResponse(List<PersonInfo> persons, int numberOfAdults, int numberOfChildren) {
        this.persons = persons;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }

    /**
     * Constructeur pour initialiser les personnes et le numéro de la caserne.
     * Les valeurs pour le nombre d'adultes et d'enfants sont optionnelles et sont initialisées à 0 par défaut.
     *
     * @param persons Liste des personnes associées à la caserne.
     * @param stationNumber Numéro de la caserne.
     */
    public FirestationResponse(List<PersonInfo> persons, String stationNumber) {
        this.persons = persons;
        this.stationNumber = stationNumber;
        this.numberOfAdults = 0; // Optionnel
        this.numberOfChildren = 0; // Optionnel
    }

    /**
     * Récupère la liste des personnes associées à la caserne.
     *
     * @return Liste des personnes.
     */
    public List<PersonInfo> getPersons() {
        return persons;
    }

    /**
     * Définit la liste des personnes associées à la caserne.
     *
     * @param persons Liste des personnes à définir.
     */
    public void setPersons(List<PersonInfo> persons) {
        this.persons = persons;
    }

    /**
     * Récupère le nombre d'adultes dans la caserne.
     *
     * @return Nombre d'adultes.
     */
    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    /**
     * Définit le nombre d'adultes dans la caserne.
     *
     * @param numberOfAdults Nombre d'adultes à définir.
     */
    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    /**
     * Récupère le nombre d'enfants dans la caserne.
     *
     * @return Nombre d'enfants.
     */
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    /**
     * Définit le nombre d'enfants dans la caserne.
     *
     * @param numberOfChildren Nombre d'enfants à définir.
     */
    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    /**
     * Récupère le numéro de la caserne.
     *
     * @return Numéro de la caserne.
     */
    public String getStationNumber() {
        return stationNumber;
    }

    /**
     * Définit le numéro de la caserne.
     *
     * @param stationNumber Numéro de la caserne à définir.
     */
    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }
}
