package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.service.DataLoaderService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordDAO {

    private final DataLoaderService dataLoaderService;
    private DataContainer dataContainer;

    // Constructeur pour initialiser DataLoaderService et charger les données dans le DataContainer
    public MedicalRecordDAO(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
        this.dataContainer = dataLoaderService.getDataContainer();  // Chargement du DataContainer via DataLoaderService
    }

    // Méthode pour récupérer tous les enregistrements médicaux
    public List<MedicalRecord> getAllMedicalRecords() {
        return dataContainer.getMedicalrecords();
    }

    // Méthode pour ajouter un nouvel enregistrement médical
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        dataContainer.getMedicalrecords().add(medicalRecord);  // Ajoute l'enregistrement à la liste
        dataLoaderService.saveData();  // Sauvegarde les données mises à jour dans le fichier JSON
    }

    // Méthode pour mettre à jour un enregistrement médical
    public Optional<MedicalRecord> updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        List<MedicalRecord> medicalRecords = dataContainer.getMedicalrecords();
        Optional<MedicalRecord> existingRecord = medicalRecords.stream()
                .filter(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName))
                .findFirst();

        if (existingRecord.isPresent()) {
            MedicalRecord medicalRecord = existingRecord.get();
            medicalRecord.setBirthdate(updatedRecord.getBirthdate());
            medicalRecord.setMedications(updatedRecord.getMedications());
            medicalRecord.setAllergies(updatedRecord.getAllergies());
            dataLoaderService.saveData();  // Sauvegarde les données après modification
            return Optional.of(medicalRecord);
        }
        return Optional.empty();
    }

    // Méthode pour supprimer un enregistrement médical
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = dataContainer.getMedicalrecords();

        // Supprimer le dossier médical en utilisant removeIf
        boolean removed = medicalRecords.removeIf(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName));

        // Si un dossier a été supprimé, on sauvegarde les nouvelles données
        if (removed) {
            dataLoaderService.saveData();  // Sauvegarde les données après suppression
        }

        return removed;
    }

    // Méthode pour récupérer un enregistrement médical par personne
    public Optional<MedicalRecord> getMedicalRecordByPerson(String firstName, String lastName) {
        // Récupérer la liste des enregistrements médicaux depuis le DataContainer
        List<MedicalRecord> medicalRecords = dataContainer.getMedicalrecords();

        // Chercher un enregistrement médical correspondant au prénom et au nom
        Optional<MedicalRecord> existingRecord = medicalRecords.stream()
                .filter(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName))
                .findFirst();

        // Retourner l'enregistrement trouvé, ou un Optional vide si aucun résultat
        return existingRecord;
    }
}
