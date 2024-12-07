package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.service.DataLoaderService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) pour gérer les enregistrements médicaux.
 * Ce DAO permet de récupérer, ajouter, mettre à jour et supprimer des enregistrements médicaux dans la base de données.
 * Utilise le DataLoaderService pour charger et sauvegarder les données.
 */
@Repository
public class MedicalRecordDAO {

    private final DataLoaderService dataLoaderService;
    private DataContainer dataContainer;

    /**
     * Constructeur pour initialiser DataLoaderService et charger les données dans le DataContainer.
     * Le DataContainer est utilisé pour accéder à tous les enregistrements médicaux.
     *
     * @param dataLoaderService Service de gestion du chargement et de la sauvegarde des données.
     */
    public MedicalRecordDAO(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
        this.dataContainer = dataLoaderService.getDataContainer();  // Chargement du DataContainer via DataLoaderService
    }

    /**
     * Récupère tous les enregistrements médicaux.
     *
     * @return Une liste de tous les enregistrements médicaux.
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        return dataContainer.getMedicalrecords();
    }

    /**
     * Ajoute un nouvel enregistrement médical.
     *
     * @param medicalRecord L'enregistrement médical à ajouter.
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        dataContainer.getMedicalrecords().add(medicalRecord);  // Ajoute l'enregistrement à la liste
        dataLoaderService.saveData();  // Sauvegarde les données mises à jour dans le fichier JSON
    }

    /**
     * Met à jour un enregistrement médical existant en fonction du prénom et du nom.
     * Si l'enregistrement est trouvé, ses informations sont mises à jour (date de naissance, médicaments, allergies).
     *
     * @param firstName Le prénom de la personne dont l'enregistrement médical doit être mis à jour.
     * @param lastName Le nom de famille de la personne dont l'enregistrement médical doit être mis à jour.
     * @param updatedRecord L'objet MedicalRecord contenant les nouvelles informations.
     * @return Un objet Optional contenant l'enregistrement médical mis à jour si trouvé, sinon un Optional vide.
     */
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

    /**
     * Supprime un enregistrement médical par le prénom et le nom de la personne.
     * Si l'enregistrement est trouvé et supprimé, les données sont sauvegardées.
     *
     * @param firstName Le prénom de la personne dont l'enregistrement médical doit être supprimé.
     * @param lastName Le nom de famille de la personne dont l'enregistrement médical doit être supprimé.
     * @return true si l'enregistrement médical a été supprimé, false sinon.
     */
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

    /**
     * Récupère un enregistrement médical spécifique basé sur le prénom et le nom de la personne.
     *
     * @param firstName Le prénom de la personne dont l'enregistrement médical doit être récupéré.
     * @param lastName Le nom de famille de la personne dont l'enregistrement médical doit être récupéré.
     * @return Un objet Optional contenant l'enregistrement médical trouvé, ou un Optional vide si aucun enregistrement n'est trouvé.
     */
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
