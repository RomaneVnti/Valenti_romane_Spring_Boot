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
        return medicalRecordDAO.getAllMedicalRecords();
    }

    // Ajouter un enregistrement médical
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordDAO.addMedicalRecord(medicalRecord);
    }

    // Mettre à jour un enregistrement médical
    public Optional<MedicalRecord> updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        return medicalRecordDAO.updateMedicalRecord(firstName, lastName, updatedRecord);
    }

    // Supprimer un enregistrement médical
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        return medicalRecordDAO.deleteMedicalRecord(firstName, lastName);
    }

    // Récupérer un enregistrement médical d'une personne
    // Récupérer un enregistrement médical d'une personne
    public Optional<MedicalRecord> getMedicalRecordByPerson(Person person) {
        // Utiliser un DAO pour récupérer l'enregistrement médical en fonction du prénom et nom
        return medicalRecordDAO.getMedicalRecordByPerson(person.getFirstName(), person.getLastName());
    }

}
