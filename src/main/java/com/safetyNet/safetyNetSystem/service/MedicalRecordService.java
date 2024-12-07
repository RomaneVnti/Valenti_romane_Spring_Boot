package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.MedicalRecordDAO;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des enregistrements médicaux.
 * Fournit des méthodes pour ajouter, mettre à jour, supprimer et récupérer des enregistrements médicaux.
 */
@Service
public class MedicalRecordService {

    private final MedicalRecordDAO medicalRecordDAO;
    private final DataLoaderUtil dataLoaderUtil;

    /**
     * Constructeur pour initialiser le DAO des enregistrements médicaux et l'utilitaire de chargement des données.
     *
     * @param medicalRecordDAO L'instance du DAO des enregistrements médicaux
     * @param dataLoaderUtil   L'utilitaire pour le chargement des données
     */
    public MedicalRecordService(MedicalRecordDAO medicalRecordDAO, DataLoaderUtil dataLoaderUtil) {
        this.medicalRecordDAO = medicalRecordDAO;
        this.dataLoaderUtil = dataLoaderUtil;
    }

    /**
     * Récupère tous les enregistrements médicaux.
     *
     * @return Une liste de tous les enregistrements médicaux
     * @throws RuntimeException Si une erreur survient lors de la récupération des enregistrements médicaux
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        try {
            return medicalRecordDAO.getAllMedicalRecords();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des enregistrements médicaux", e);
        }
    }

    /**
     * Ajoute un nouvel enregistrement médical.
     *
     * @param medicalRecord L'enregistrement médical à ajouter
     * @throws IllegalArgumentException Si l'enregistrement médical est null
     * @throws RuntimeException         Si une erreur survient lors de l'ajout de l'enregistrement médical
     */
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

    /**
     * Met à jour un enregistrement médical existant.
     *
     * @param firstName      Le prénom de la personne associée à l'enregistrement
     * @param lastName       Le nom de famille de la personne associée à l'enregistrement
     * @param updatedRecord  L'enregistrement médical mis à jour
     * @return Un Optional contenant l'enregistrement médical mis à jour, ou vide si l'enregistrement n'existe pas
     * @throws IllegalArgumentException Si l'un des paramètres est null
     * @throws RuntimeException         Si une erreur survient lors de la mise à jour de l'enregistrement médical
     */
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

    /**
     * Supprime un enregistrement médical.
     *
     * @param firstName Le prénom de la personne associée à l'enregistrement
     * @param lastName  Le nom de famille de la personne associée à l'enregistrement
     * @return true si l'enregistrement a été supprimé, false sinon
     * @throws IllegalArgumentException Si les paramètres fournis sont null
     * @throws RuntimeException         Si une erreur survient lors de la suppression de l'enregistrement médical
     */
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

    /**
     * Récupère l'enregistrement médical d'une personne donnée.
     *
     * @param person La personne pour laquelle récupérer l'enregistrement médical
     * @return Un Optional contenant l'enregistrement médical, ou vide si aucun enregistrement n'est trouvé
     * @throws IllegalArgumentException Si la personne est null
     * @throws RuntimeException         Si une erreur survient lors de la récupération de l'enregistrement médical
     */
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
