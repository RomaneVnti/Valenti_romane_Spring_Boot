package com.safetyNet.safetyNetSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Classe représentant les informations personnelles d'une personne.
 * Elle inclut des informations de base comme le prénom, le nom, l'adresse, le téléphone
 * ainsi que des informations médicales supplémentaires.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonInfo {
    private String firstName;   // Prénom de la personne
    private String lastName;    // Nom de la personne
    private String address;     // Adresse de la personne
    private String phone;       // Numéro de téléphone de la personne
    private MedicalInfo medicalInfo;  // Informations médicales supplémentaires de la personne

    /**
     * Constructeur pour initialiser les informations personnelles de la personne.
     * Les informations médicales sont initialisées séparément.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de la personne.
     * @param address L'adresse de la personne.
     * @param phone Le numéro de téléphone de la personne.
     */
    public PersonInfo(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
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
     * @param firstName Le prénom de la personne.
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
     * @param lastName Le nom de la personne.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Récupère l'adresse de la personne.
     *
     * @return L'adresse de la personne.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse de la personne.
     *
     * @param address L'adresse de la personne.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Récupère le numéro de téléphone de la personne.
     *
     * @return Le numéro de téléphone de la personne.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Définit le numéro de téléphone de la personne.
     *
     * @param phone Le numéro de téléphone de la personne.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Récupère les informations médicales supplémentaires de la personne.
     *
     * @return Les informations médicales de la personne.
     */
    public MedicalInfo getMedicalInfo() {
        return medicalInfo;
    }

    /**
     * Définit les informations médicales supplémentaires de la personne.
     *
     * @param medicalInfo Les informations médicales à définir.
     */
    public void setMedicalInfo(MedicalInfo medicalInfo) {
        this.medicalInfo = medicalInfo;
    }
}
