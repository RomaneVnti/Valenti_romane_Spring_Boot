package com.safetyNet.safetyNetSystem.model;

import java.util.List;

/**
 * Classe représentant un conteneur pour les données du système de sécurité.
 * Elle contient des listes d'objets représentant des personnes, des casernes de pompiers et des dossiers médicaux.
 */
public class DataContainer {
    private List<Person> persons;          // Liste des personnes du système
    private List<Firestation> firestations; // Liste des casernes de pompiers
    private List<MedicalRecord> medicalrecords; // Liste des dossiers médicaux

    /**
     * Récupère la liste des personnes.
     *
     * @return La liste des personnes.
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Définit la liste des personnes.
     *
     * @param persons La liste des personnes à définir.
     */
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    /**
     * Récupère la liste des casernes de pompiers.
     *
     * @return La liste des casernes de pompiers.
     */
    public List<Firestation> getFirestations() {
        return firestations;
    }

    /**
     * Définit la liste des casernes de pompiers.
     *
     * @param firestations La liste des casernes de pompiers à définir.
     */
    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    /**
     * Récupère la liste des dossiers médicaux.
     *
     * @return La liste des dossiers médicaux.
     */
    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    /**
     * Définit la liste des dossiers médicaux.
     *
     * @param medicalrecords La liste des dossiers médicaux à définir.
     */
    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}
