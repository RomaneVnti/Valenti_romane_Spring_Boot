package com.safetyNet.safetyNetSystem.dto;

import java.util.List;

/**
 * Réponse de l'API contenant les informations relatives aux personnes associées à une caserne de pompiers,
 * sans les informations de comptage des adultes et des enfants.
 */
public class FirestationResponseNoCount {
    private List<PersonInfo> persons; // Liste des personnes associées à la caserne
    private String stationNumber;     // Numéro de la caserne

    /**
     * Constructeur par défaut, requis pour la sérialisation avec Jackson et pour certaines instanciations.
     */
    public FirestationResponseNoCount() {
    }

    /**
     * Constructeur pour initialiser les personnes et le numéro de la caserne.
     *
     * @param persons Liste des personnes associées à la caserne.
     * @param stationNumber Numéro de la caserne.
     */
    public FirestationResponseNoCount(List<PersonInfo> persons, String stationNumber) {
        this.persons = persons;
        this.stationNumber = stationNumber;
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
