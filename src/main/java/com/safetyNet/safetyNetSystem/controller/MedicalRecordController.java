package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller pour gérer les opérations liées aux dossiers médicaux.
 */
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Ajouter un nouveau dossier médical.
     *
     * @param medicalRecord les informations du dossier médical à ajouter
     * @return une réponse HTTP indiquant si l'ajout a réussi
     */
    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.debug("Received request to add medical record: {}", medicalRecord);

        try {
            medicalRecordService.addMedicalRecord(medicalRecord);
            logger.info("Medical record added successfully: {}", medicalRecord);
            return ResponseEntity.ok("Medical record added successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while adding medical record: {}", medicalRecord, e);
            return ResponseEntity.status(500).body("Failed to add medical record.");
        }
    }

    /**
     * Mettre à jour un dossier médical existant pour une personne donnée.
     *
     * @param firstName le prénom de la personne
     * @param lastName le nom de famille de la personne
     * @param updatedRecord les nouvelles informations du dossier médical
     * @return une réponse HTTP indiquant si la mise à jour a réussi
     */
    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord updatedRecord) {

        logger.debug("Received request to update medical record for: {} {}", firstName, lastName);

        try {
            Optional<MedicalRecord> updated = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedRecord);

            if (updated.isPresent()) {
                logger.info("Medical record updated successfully for: {} {}", firstName, lastName);
                return ResponseEntity.ok("Medical record updated successfully.");
            } else {
                logger.warn("Medical record not found for: {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating medical record for: {} {}", firstName, lastName, e);
            return ResponseEntity.status(500).body("Failed to update medical record.");
        }
    }

    /**
     * Supprimer un dossier médical existant pour une personne donnée.
     *
     * @param firstName le prénom de la personne
     * @param lastName le nom de famille de la personne
     * @return une réponse HTTP indiquant si la suppression a réussi
     */
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {

        logger.debug("Received request to delete medical record for: {} {}", firstName, lastName);

        try {
            boolean removed = medicalRecordService.deleteMedicalRecord(firstName, lastName);

            if (removed) {
                logger.info("Medical record deleted successfully for: {} {}", firstName, lastName);
                return ResponseEntity.ok("Medical record deleted successfully.");
            } else {
                logger.warn("Medical record not found for: {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting medical record for: {} {}", firstName, lastName, e);
            return ResponseEntity.status(500).body("Failed to delete medical record.");
        }
    }
}
