package com.safetyNet.safetyNetSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class MedicalInfo {
    private List<String> medications;
    private List<String> allergies;

    public MedicalInfo(List<String> medications, List<String> allergies) {
        this.medications = medications;
        this.allergies = allergies;
    }

    // Getters et Setters
    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
