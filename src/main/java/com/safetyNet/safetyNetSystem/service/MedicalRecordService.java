package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.MedicalRecordDAO;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordDAO medicalRecordDAO;
    private final DataLoaderUtil dataLoaderUtil;

    // Constructeur pour initialiser le DAO et DataLoaderUtil
    public MedicalRecordService(MedicalRecordDAO medicalRecordDAO, DataLoaderUtil dataLoaderUtil) {
        this.medicalRecordDAO = medicalRecordDAO;
        this.dataLoaderUtil = dataLoaderUtil;
    }

    // Récupérer tous les enregistrements médicaux
    public List<MedicalRecord> getAllMedicalRecords() {
        try {
            return medicalRecordDAO.getAllMedicalRecords();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des enregistrements médicaux", e);
        }
    }

    // Ajouter un enregistrement médical
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord == null) {
            throw new IllegalArgumentException("L'enregistrement médical ne peut pas être null");
        }

        try {
            medicalRecordDAO.addMedicalRecord(medicalRecord);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'enregistrement médical", e);
        }
    }

    // Mettre à jour un enregistrement médical
    public Optional<MedicalRecord> updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        if (firstName == null || lastName == null || updatedRecord == null) {
            throw new IllegalArgumentException("Les paramètres fournis ne peuvent pas être null");
        }

        try {
            return medicalRecordDAO.updateMedicalRecord(firstName, lastName, updatedRecord);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'enregistrement médical", e);
        }
    }

    // Supprimer un enregistrement médical
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Les paramètres fournis ne peuvent pas être null");
        }

        try {
            return medicalRecordDAO.deleteMedicalRecord(firstName, lastName);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'enregistrement médical", e);
        }
    }

    // Récupérer un enregistrement médical d'une personne
    public Optional<MedicalRecord> getMedicalRecordByPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("La personne ne peut pas être null");
        }

        try {
            return medicalRecordDAO.getMedicalRecordByPerson(person.getFirstName(), person.getLastName());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de l'enregistrement médical pour la personne " + person.getFirstName() + " " + person.getLastName(), e);
        }
    }
}
