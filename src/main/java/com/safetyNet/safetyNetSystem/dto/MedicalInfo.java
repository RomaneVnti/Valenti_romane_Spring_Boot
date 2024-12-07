package com.safetyNet.safetyNetSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Classe représentant les informations médicales d'une personne, y compris les médicaments et les allergies.
 * Les listes de médicaments et d'allergies peuvent être nulles ou vides.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalInfo {
    private List<String> medications; // Liste des médicaments
    private List<String> allergies;   // Liste des allergies

    /**
     * Constructeur pour initialiser les informations médicales avec des médicaments et des allergies.
     *
     * @param medications Liste des médicaments que prend la personne.
     * @param allergies Liste des allergies de la personne.
     */
    public MedicalInfo(List<String> medications, List<String> allergies) {
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * Récupère la liste des médicaments de la personne.
     *
     * @return Liste des médicaments.
     */
    public List<String> getMedications() {
        return medications;
    }

    /**
     * Définit la liste des médicaments de la personne.
     *
     * @param medications Liste des médicaments à définir.
     */
    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    /**
     * Récupère la liste des allergies de la personne.
     *
     * @return Liste des allergies.
     */
    public List<String> getAllergies() {
        return allergies;
    }

    /**
     * Définit la liste des allergies de la personne.
     *
     * @param allergies Liste des allergies à définir.
     */
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
