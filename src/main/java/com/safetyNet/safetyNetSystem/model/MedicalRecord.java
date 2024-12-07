package com.safetyNet.safetyNetSystem.model;

import java.util.List;

/**
 * Classe représentant un dossier médical d'une personne.
 * Elle contient des informations telles que le nom, la date de naissance, les médicaments et les allergies d'une personne.
 */
public class MedicalRecord {

    private String firstName;  // Prénom de la personne
    private String lastName;   // Nom de la personne
    private String birthdate;  // Date de naissance de la personne
    private List<String> medications;  // Liste des médicaments de la personne
    private List<String> allergies;    // Liste des allergies de la personne

    /**
     * Constructeur par défaut pour la classe MedicalRecord.
     * Nécessaire pour certaines instanciations, comme celles avec des frameworks de sérialisation.
     */
    public MedicalRecord() {
    }

    /**
     * Constructeur pour initialiser un dossier médical avec des informations de prénom, nom,
     * date de naissance, médicaments et allergies.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de la personne.
     * @param birthdate La date de naissance de la personne.
     * @param medications Liste des médicaments de la personne.
     * @param allergies Liste des allergies de la personne.
     */
    public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * Récupère le prénom de la personne.
     *
     * @return Le prénom de la personne.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom de la personne.
     *
     * @param firstName Le prénom à définir.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Récupère le nom de la personne.
     *
     * @return Le nom de la personne.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom de la personne.
     *
     * @param lastName Le nom à définir.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Récupère la date de naissance de la personne.
     *
     * @return La date de naissance de la personne.
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Définit la date de naissance de la personne.
     *
     * @param birthdate La date de naissance à définir.
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Récupère la liste des médicaments de la personne.
     *
     * @return La liste des médicaments de la personne.
     */
    public List<String> getMedications() {
        return medications;
    }

    /**
     * Définit la liste des médicaments de la personne.
     *
     * @param medications La liste des médicaments à définir.
     */
    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    /**
     * Récupère la liste des allergies de la personne.
     *
     * @return La liste des allergies de la personne.
     */
    public List<String> getAllergies() {
        return allergies;
    }

    /**
     * Définit la liste des allergies de la personne.
     *
     * @param allergies La liste des allergies à définir.
     */
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    /**
     * Méthode toString() pour obtenir une représentation sous forme de chaîne de caractères
     * du dossier médical de la personne.
     *
     * @return Une chaîne de caractères représentant l'objet MedicalRecord.
     */
    @Override
    public String toString() {
        return "MedicalRecord{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
