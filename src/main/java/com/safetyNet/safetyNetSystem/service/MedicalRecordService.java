package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final DataContainer dataContainer;
    private final DataLoaderUtil dataLoaderUtil; // Ajout d'une instance de DataLoaderUtil

    public MedicalRecordService(DataLoaderService dataLoaderService, DataLoaderUtil dataLoaderUtil) {
        this.dataContainer = dataLoaderService.loadData(); // Charger les données au démarrage
        this.dataLoaderUtil = dataLoaderUtil; // Initialiser DataLoaderUtil
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataContainer.getMedicalrecords(); // Utilisez le bon nom ici
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        dataContainer.getMedicalrecords().add(medicalRecord);
        dataLoaderUtil.saveData(dataContainer); // Sauvegarder les données après ajout
    }

    public Optional<MedicalRecord> updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        List<MedicalRecord> medicalRecords = dataContainer.getMedicalrecords();

        Optional<MedicalRecord> existingRecord = medicalRecords.stream()
                .filter(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName))
                .findFirst();

        if (existingRecord.isPresent()) {
            MedicalRecord record = existingRecord.get();
            record.setBirthdate(updatedRecord.getBirthdate());
            record.setMedications(updatedRecord.getMedications());
            record.setAllergies(updatedRecord.getAllergies());
            dataLoaderUtil.saveData(dataContainer); // Sauvegarder les données après mise à jour
            return Optional.of(record);
        }
        return Optional.empty();
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = dataContainer.getMedicalrecords();
        boolean removed = medicalRecords.removeIf(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName));

        if (removed) {
            dataLoaderUtil.saveData(dataContainer); // Sauvegarder les données après suppression
        }
        return removed;
    }
}
